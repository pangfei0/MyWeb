define(["jquery"], function ($) {

    function getUrlParameter(sParam) {
        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
            sURLVariables = sPageURL.split('&'),
            sParameterName,
            i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : sParameterName[1];
            }
        }
    }
    return {
        /**
         * 从页面中加载预先定义好的一些模板HTML，并使用dataMap中的键值对替换其中的一些变量
         */
        loadPageTemplate: function (templateId, dataMap) {
            var template = $("#" + templateId).html();
            for (var key in dataMap) {
                var value = dataMap[key];
                var replaceVariable = "{" + key + "}";
                template = template.replace(new RegExp(replaceVariable, "g"), value);
            }
            return template;
        },
        /**
         * 获得URL参数，如果不存在则返回undefined
         */
        getUrlParameter: function (paramter) {
            return getUrlParameter(paramter);
        },
        isUrlParameterExist: function (paramter) {
            return typeof getUrlParameter(paramter) !== "undefined";
        }
    };
});