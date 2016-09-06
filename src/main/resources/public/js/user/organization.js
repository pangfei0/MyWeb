define(["jquery", "vue", "ajax", "ztree"], function ($, Vue, ajax, ztree) {
    function init() {
        $(".organization").addClass("active");
        refreshTree();
    }

    function refreshTree() {
        var setting = {
            view: {
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            edit: {
                enable: true,
                editNameSelectAll: true,
                showRemoveBtn: showRemoveBtn,
                showRenameBtn: function () {
                    return false;
                }
            },
            callback: {
                beforeEditName: function () {
                    return true;
                },
                beforeRemove: beforeRemove,
                onRemove: onRemove,
                beforeRename: beforeRename
            }
        };

        ajax.get("/api/organization/tree", function (res) {
            $.fn.zTree.init($("#tree"), setting, res.data);
        });
    }

    function addHoverDom(treeId, treeNode) {
        var nodeTxt = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;

        var editStr = "<span class='button edit' id='editBtn_" + treeNode.tId + "' onfocus='this.blur();'></span>";
        nodeTxt.after(editStr);
        var btnEdit = $("#editBtn_" + treeNode.tId);
        if (btnEdit) {
            btnEdit.bind("click", function () {
                onEditNode(treeNode);
                return false;
            });
        }

        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' onfocus='this.blur();'></span>";
        nodeTxt.after(addStr);
        var btn = $("#addBtn_" + treeNode.tId);
        if (btn) {
            btn.bind("click", function () {
                onAddNewNode(treeNode);
                return false;
            });
        }

    }

    function onAddNewNode(treeNode) {
        window.location.href = "/organization/cou?parent=" + treeNode.id;
    }

    function onEditNode(treeNode) {
        window.location.href = "/organization/cou?id=" + treeNode.id;
    }

    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_" + treeNode.tId).unbind().remove();
        $("#editBtn_" + treeNode.tId).unbind().remove();
    }

    function beforeRemove(treeId, treeNode) {
        return confirm("确认删除" + treeNode.name + " 吗？");
    }

    function onRemove(e, treeId, treeNode) {
        ajax.delete("/api/organization/" + treeNode.id, function (res) {
            refreshTree();
        });

        return false;
    }

    function beforeRename() {
        return false;
    }

    function showRemoveBtn(treeId, treeNode) {
        return true;
    }

    init();
});