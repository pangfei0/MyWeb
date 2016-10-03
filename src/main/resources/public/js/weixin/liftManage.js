define(['jquery','vue','ajax'],function($,Vue,ajax){
	/*搜素框*/
    $('#container').on('focus', '#search_input', function () {
        var $weuiSearchBar = $('#search_bar');
        $weuiSearchBar.addClass('weui-search-bar_focusing');
    }).on('blur', '#search_input', function () {
        var $weuiSearchBar = $('#search_bar');
        $weuiSearchBar.removeClass('weui-search-bar_focusing');
        if ($(this).val()) {
            $('#search_text').hide();
        } else {
            $('#search_text').show();
        }
    }).on('input', '#search_input', function () {
        var $searchShow = $("#search_show");
        if ($(this).val()) {
            $searchShow.show();
        } else {
            $searchShow.hide();
        }
    }).on('touchend', '#search_cancel', function () {
        $("#search_show").hide();
        $('#search_input').val('');
    }).on('touchend', '#search_clear', function () {
        $("#search_show").hide();
        $('#search_input').val('');
    });
});