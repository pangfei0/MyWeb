define(["jquery", "table", "js/dynamicSearch"], function ($, bootstrapTable, dynamicSearch) {
    return {
        createTableG: function (elementId, url, definitions) {
            var config = {
                url: url,
                locale: "zh-CN",
                //pagination: true,
                //pageList: [10, 15, 50, 100],
                sidePagination: "server",
                //pageSize: 10,
                sortable:true,
                search: false,
                method: "get",
                contentType: "application/x-www-form-urlencoded",
                queryParamsType: "nolimit",
                queryParams: function (params) {
                    //默认搜索.search-form下面的字段
                    return $.extend(params, dynamicSearch.buildSearchJson(".search-form"));
                },
                responseHandler: function (apiResponseData) {
                    return {
                        "total": apiResponseData.data.length,
                        "rows": apiResponseData.data
                    };
                }
            };

            var mergedConfig = $.extend(config, definitions);
            return $("#" + elementId).bootstrapTable(mergedConfig);
        }
    };
});