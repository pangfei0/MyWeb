define(["jquery", "ajax", "translationData"], function ($, ajax, translations) {
    function getCurrentLanguage() {
        return $("#currentLanguage").val();
    }

    return {
        translate: function (code) {
            if (typeof translations[code] !== "undefined") {
                return translations[code][getCurrentLanguage()];
            }

            return "Empty Translation";
        }
    }
});