define(["jquery"], function ($) {
    function getSearchField($element) {
        var searchField = $element.attr("data-search-field");
        if (!searchField) {
            searchField = $element.attr("id");
        }
        return searchField;
    }

    function getSearchOperation($element) {
        if (typeof $element.attr("data-search-operation") !== "undefined") {
            return $element.attr("data-search-operation").toUpperCase();
        }
        return "LIKE"; //默认为模糊匹配
    }

    function getSearchValue($element) {
        var searchValue = $element.val();
        var searchValueFormatter = $element.attr("data-search-value-formatter");
        if (searchValueFormatter) {
            searchValue = window[searchValueFormatter]();
        }
        return searchValue;
    }

    function getSearchDataType($element) {
        var dataType = $element.attr("data-search-type");
        if (!dataType) {
            dataType = "TEXT";
        }
        return dataType.toUpperCase();
    }

    return {
        buildSearchJson: function (containerId) {
            var searchList = [];
            $(containerId + " input[type='text']," + containerId + " input[type='hidden']").each(function () {
                var $txt = $(this);
                var searchValue = getSearchValue($txt);
                //不添加值为空的输入框
                if (searchValue !== "" && !$txt.attr("data-search-ignore")) {
                    searchList.push({
                        "dataType": getSearchDataType($txt),
                        "field": getSearchField($txt),
                        "operation": getSearchOperation($txt),
                        "value": searchValue
                    });
                }
            });

            var returnJson = {};
            $.each(searchList, function (index, val) {
                returnJson["search_" + val.field + "(" + val.dataType + ")_" + val.operation] = val.value;
            });
            return returnJson;
        }
    };
});