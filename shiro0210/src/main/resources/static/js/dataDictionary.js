/**
 * Created by Administrator on 2016/12/19.
 */
requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'treemenu', 'base'], function ($, bootstrap, template, treemenu, base) {
        var parentId = 0;
        $('body').on('click', '.root', function () {
            var id = $(this).attr('id');
            parentId = id;
            getCatalogData(id);
        });

        $('body').on('click', '.second', function () {
            var id = $(this).attr('id');
            parentId = id;
            getCatalogData(id);
        });

        $('body').on('click', '.third', function () {
            var id = $(this).attr('id');
            parentId = id;
            getContentData(id);
        });

        // 加载字典树
        loadCataTree(-1);
        function loadCataTree(parId) {
            base.sendReq({
                url: 'dictionary/catalog',
                method: 'POST',
                getResult: function (data) {
                    if (data.success) {
                        var cataHtml = template('dicCatalog', data);
                        $('#treeCatalog').html(cataHtml);

                        if (parId == -1) {
                            $('.root').addClass('active');
                            var id = data.catalogShowVoList[0].id;
                            parentId = id;
                            getCatalogData(id);
                        } else {
                            $('.root').removeClass('active');
                            $('#'+parId).addClass('active');
                        }
                        $(".tree").treemenu({delay:300}).openActive();

                    } else {
                        base.showAlertDialog(data.errorMessage);
                    }
                }
            });
        }


        // 获取目录的数据
        function getCatalogData(id) {
            base.sendReq({
                url: 'dictionary/fetchCatalog/id/'+id,
                method: 'GET',
                getResult: function (data) {
                    if (data.success) {
                        var rootHtml1 = template('dictType', data);
                        $('#dictTable').html(rootHtml1);
                    } else {
                        if (data.errorCode === 200004) {
                            var emptyData = {
                                catalogList:[]
                            };
                            var emptyHtml = template('dictType', emptyData);
                            $('#dictTable').html(emptyHtml);
                        } else {
                            base.showAlertDialog(data.errorMessage);
                        }
                    }
                }
            });
        }

        // 获取字典内容数据
        function getContentData (id) {
            base.sendReq({
                url: 'dictionary/fetchDictionary/catalog/'+id,
                method: 'GET',
                getResult: function (data) {
                    if (data.success) {
                        var detailHtml = template('dictDetail', data);
                        $('#dictTable').html(detailHtml);
                    } else {
                        if (data.errorCode === 200004) {
                            var emptyData = {
                                dictionaryList:[]
                            };
                            var emptyHtml = template('dictDetail', emptyData);
                            $('#dictTable').html(emptyHtml);
                        } else {
                            base.showAlertDialog(data.errorMessage);
                        }
                    }
                }
            });
        }

        $('#addBtn').on('click', function () {
            var bodyId = $('#dictTable').children('tbody').attr('id');
            var addHtml = '';
            if (bodyId == 'dictTypeTableBody') {
                addHtml = '<tr><td><input name="dictionary" type="checkbox" class="list-check"></td><td></td><td class="dict-id hide"><input type="text" value=""></td><td><input type="text" class="table-input t-required" value=""></td>'+
                    '<td><input type="text" class="table-input t-required" value=""></td><td><input type="text" class="table-input t-required" value=""></td></tr>';
            } else {
                addHtml = '<tr><td><input name="dictionary" type="checkbox" class="list-check"></td><td></td><td class="dict-id hide"><input type="text" value=""></td><td><input type="text" class="table-input t-required" value=""></td>'+
                    '<td><input type="text" class="table-input t-required" value=""></td><td><input type="text" class="table-input t-required" value=""></td></tr>';
            }
            $('#'+ bodyId).append(addHtml);
        });

        $('#delBtn').on('click', function () {
            var dict = $("input[name=dictionary]:checked"), dictArr = [], url = '', caOrCon = 0;
            if (dict.size() == 0) {
                base.showAlertDialog('请先选中要删除的行');
                return;
            }
            dict.each(function () {
                var delId = $(this).parent().siblings('.dict-id').children().val();
                dictArr.push(delId);
                $(this).parent().parent().remove();

            });
            var bodyId = $('#dictTable').children('tbody').attr('id');
            if (bodyId == 'dictTypeTableBody') {
                url = 'dictionary/catalogDelete';
                caOrCon = 0;
            }else {
                url = 'dictionary/dictionaryDelete';
                caOrCon = 1;
            }
            
            dictArr = JSON.stringify(dictArr);
            $('#dictionaryDialogBox').dialogBox({
                type: 'correct',  //three type:'normal'(default),'correct','error',
                width: 300,
                hasMask: true,
                hasClose: true,
                hasBtn: true,
                cancelValue: '取消',
                confirmValue: '确定',
                confirm: function () {
                    base.sendReq({
                        url: url,
                        data: dictArr,
                        headers: {
                            'Content-Type': 'application/json;charset=UTF-8'
                        },
                        method: 'DELETE',
                        getResult: function (data) {
                            if (data.success) {
                                base.showAlertDialog('删除成功');
                                loadCataTree(parentId);
                                if (caOrCon == 0) {
                                    getCatalogData(parentId);
                                } else {
                                    getContentData(parentId);
                                }
                            } else {
                                base.showAlertDialog(data.errorMessage);
                            }
                        }
                    });
                },
                effect: 'sign',
                title: '提示',
                content: '是否删除选中项？'});
        });

        $('#saveBtn').on('click', function () {
            var data = [], isCorrect = true;
            $("#dictTable tr:not(:first)").each(function (i) {
                $(this).children("td").each(function (j) {
                    if(!data[i]){
                        data[i] = new Array();
                    }
                    if ((j == 3 || j == 4 || j == 5) && base.isStringNull($(this).children().val())) {
                        isCorrect = false;
                        var tipHtml = '<div class="dark-tooltip dark medium south left-10 top_40"><div class="tooltip-info">该栏位为必填项</div><div class="tip"></div></div>';
                        $(this).append(tipHtml);
                        $(this).children('.dark-tooltip').css('display', 'block');
                        return false;
                    } else {
                        data[i][j] = $(this).children().val();
                    }
                });
                if (!isCorrect) {
                    return false;
                }
            });
            
            if (isCorrect) {
                var bodyId = $('#dictTable').children('tbody').attr('id'), caOrCon = 0;
                var obj = {}, typeArr = [], url = '';
                if (bodyId == 'dictTypeTableBody') {
                    url = 'dictionary/updateOrInsertCatalog';
                    caOrCon = 0;
                    for (var i = 0; i < data.length; i++) {
                        obj = {
                            id: data[i][2],
                            code: data[i][3],
                            name: data[i][4],
                            description: data[i][5],
                            parentId: parentId
                        };
                        typeArr.push(obj);
                    }
                } else {
                    url = 'dictionary/updateOrInsertDictionary';
                    caOrCon = 1;
                    for (var j = 0; j < data.length; j++) {
                        obj = {
                            id: data[j][2],
                            code: data[j][3],
                            name: data[j][4],
                            description: data[j][5],
                            catalogId: parentId
                        };
                        typeArr.push(obj);
                    }
                }
                typeArr = JSON.stringify(typeArr);
                base.sendReq({
                    url: url,
                    method: 'POST',
                    data: typeArr,
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    },
                    getResult: function (data) {
                        if (data.success) {
                            if (data.codeRepeat.length > 0 || data.nameRepeat.length > 0) {
                                if (data.codeRepeat.length > 0) {
                                    var repeat = '';
                                    for (var i = 0; i < data.codeRepeat.length; i++) {
                                        repeat += data.codeRepeat[i];
                                        if (i < data.codeRepeat.length -1) {
                                            repeat += '，';
                                        }
                                    }
                                    repeat += '，存在编码重名，';
                                }
                                if (data.nameRepeat.length > 0) {
                                    for (var j = 0; j < data.nameRepeat.length; j++) {
                                        repeat += data.nameRepeat[j];
                                        if (j < data.nameRepeat.length -1) {
                                            repeat += '，';
                                        }
                                    }
                                    repeat += '，存在名称重名，';
                                }
                                repeat += '请修改！';
                                base.showAlertDialog(repeat);
                            } else {
                                base.showAlertDialog('保存成功');
                                loadCataTree(parentId);
                                if (caOrCon == 0) {
                                    getCatalogData(parentId);
                                } else {
                                    getContentData(parentId);
                                }
                            }

                        } else {
                            base.showAlertDialog(data.errorMessage);
                        }
                    }
                });
            }

        });
        
        $('body').on('focus', '.t-required', function () {
            $(this).siblings('.dark-tooltip').remove();
        });
    });
});