package juli.service;

import juli.domain.Translation;
import juli.domain.enums.Language;
import juli.repository.TranslationRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TranslationService {

    /**
     * 网站默认语言
     */
    Language DEFAULT_LANGUAGE = Language.CHINESE;
    static HashMap<String, Translation> translations = null;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    TranslationRepository translationRepository;

    public HashMap<String, Translation> getAllTranslations() {
        if (translations == null) {
            translations = new HashMap<>();
            for (Translation t : translationRepository.findAll()) {
                translations.put(t.getCode(), t);
            }
            logger.info("loaded " + translations.size() + " translation");
        }

        return translations;
    }

    public String getTranslation(String code) {
        Session session = SecurityUtils.getSubject().getSession();
        if (session.getAttribute("currentLanguage") == null) {
            session.setAttribute("currentLanguage", DEFAULT_LANGUAGE.name());
        }
        String currentLanguage = session.getAttribute("currentLanguage").toString();
        return getTranslation(code, Language.valueOf(currentLanguage.toUpperCase()));
    }

    public String getTranslation(String code, Language language) {
        Translation translation = getAllTranslations().get(code);
        if (translation == null) {
            return "empty translation";
        }

        switch (language) {
            case ENGLISH:
                return translation.getEnglish();
            case CHINESE:
                return translation.getChinese();
        }

        return "invalid language";
    }

    public void changeLanguage(String code) {
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("currentLanguage", code);
    }

    public String getCurrentLanguage() {
        try {
            Session session = SecurityUtils.getSubject().getSession();
            Object currentLanguage = session.getAttribute("currentLanguage");
            if (currentLanguage == null) {
                return DEFAULT_LANGUAGE.name().toLowerCase();
            }

            return currentLanguage.toString().toLowerCase();
        } catch (UnavailableSecurityManagerException e) {
            return DEFAULT_LANGUAGE.name().toLowerCase();
        }
    }
}