define(["jquery", "toastr"], function ($, toastr) {

    function isValidResponse(response) {
        if (!response)
            return false;

        if (typeof response.success !== "undefined") {
            return response && response.success;
        }
        else {
            return response != "";
        }
    }

    function handleInvalidResponse(response) {
        if (response.description != "") {
            toastr.error(response.description);
        }
    }

    return {
        post: function (relativeUrl, postData, successCallback) {
            return $.ajax({
                type: "POST",
                url: relativeUrl,
                data: postData,
                success: function (data) {
                    if (!isValidResponse(data)) {
                        handleInvalidResponse(data);
                        return;
                    }

                    if (successCallback) {
                        successCallback(data);
                    }
                }
            });
        },
        get: function (relativeUrl, successCallback) {
            return $.ajax({
                type: "GET",
                url: relativeUrl,
                success: function (data) {
                    if (!isValidResponse(data)) {
                        handleInvalidResponse(data);
                        return;
                    }

                    if (successCallback) {
                        successCallback(data);
                    }
                }
            });
        },
        delete: function (relativeUrl, successCallback) {
            return $.ajax({
                url: relativeUrl,
                type: 'DELETE',
                success: function (data) {
                    if (!isValidResponse(data)) {
                        handleInvalidResponse(data);
                        return;
                    }

                    if (successCallback) {
                        successCallback(data);
                    }
                }
            });
        },
        patch: function (relativeUrl, patchData, successCallback) {
            return $.ajax({
                url: relativeUrl,
                type: 'PATCH',
                data: patchData,
                success: function (data) {
                    if (!isValidResponse(data)) {
                        handleInvalidResponse(data);
                        return;
                    }

                    if (successCallback) {
                        successCallback(data);
                    }
                }
            });
        }
    };
});