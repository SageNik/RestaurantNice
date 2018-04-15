$(document).ready(
    function () {
        $('#orders').click(function () {
            $('#content').load("orders");
            return false;
        });
    });

$(document).ready(
    function () {
        $('#menu').click(function () {
            $('#content').load("menu");
            return false;
        });
    });

$(document).ready(
    function () {
        $('#groups').click(function () {
            $('#content').load("groups");
            return false;
        });
    });

$(document).ready(
    function () {
        $('#cancelRegistration').click(function () {
            window.location.href ="login";
            return false;
        });
    });


    function moveToMenu() {
        $('#content').load("menu");
        return false;
    }

    function moveNewOrder() {
            $('#content').load("newOrder");
            return false;
    }

function moveNewGroup() {
    $('#in-content').load("newGroup");
    return false;
}

function moveToGroups() {
    $('#content').load("groups");
    return false;
}

function toJoinRequests() {
    $('#content').load("joinRequests");
    return false;
}

function onlyNumber(obj) {
    obj = (obj) ? obj : window.obj;
    var charCode = (obj.which) ? obj.which : obj.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function toOrder(dish_id, amount) {
    $('#toOrder').prop("disabled",true);
    $.post("toOrder",{dishId: dish_id, amount: amount}
    ).done(function (data){
        $('#right-block-content-order').html(data)
    }).fail(function () {
        alert("Sorry, order row has not been added to order")
    }).always(function () {
        $('#toOrder').prop("disabled",false);
    })
}

function fromOrder(dish_id) {
    $('#fromOrder').prop("disabled",true);
    $.post("fromOrder",{dishId: dish_id}
    ).done(function (data) {
        $('#right-block-content-order').html(data)
    }).fail(function () {
        alert("Sorry, order row has not been deleted from order")
    }).always(function () {
        $('#fromOrder').prop("disabled",false);
    })
}

function saveOrder() {
    $('#saveOrder').prop("disabled",true);
    $.post("saveOrder"
    ).done(function (data) {
        alert("Order has been successfully saved");
        $('#content').html(data)
    }).fail(function () {
        alert("Sorry, order has not been saved")
    }).always(function () {
        $('#saveOrder').prop("disabled",false);
    })
}

function saveAndSendOrder() {
    $('#saveAndSendOrder').prop("disabled",true);
    $.post("saveAndSendOrder"
    ).done(function (data) {
        alert("Order has been successfully saved & sent");
        $('#content').html(data)
    }).fail(function () {
        alert("Sorry, order has not been saved & sent")
    }).always(function () {
        $('#saveAndSendOrder').prop("disabled",false);
    })
}

function showOrder(order_id) {
    $('#showOrder').prop("disabled", true);
    $.post("showOrder",{orderId: order_id}
    ).done(function (data) {
        $('#right-block-content').html(data)
    }).fail(function () {
        alert("Sorry, order could not be shown")
    }).always(function () {
        $('#showOrder').prop("disabled", false);
    })
}

function sendOrder(order_id) {
    $('#sendOrder').prop("disabled", true);
    $.post("sendOrder",{orderId: order_id}
    ).done(function (data) {
        $('#right-block-content').html(data)
    }).fail(function () {
        alert("Sorry, order could not be sent")
    }).always(function () {
        $('#sendOrder').prop("disabled", false);
    })
}

function deleteOrder(order_id) {
    var conf = confirm("Do you really want to delete order with id:"+order_id+"?");
   if(conf) {
       $('#deleteOrder').prop("disabled", true);
       $.post("deleteOrder", {orderId: order_id}
       ).done(function (data) {
           $('#content').html(data)
       }).fail(function () {
           alert("Sorry, order could not be deleted")
       }).always(function () {
           $('#deleteOrder').prop("disabled", false);
       })
   }
}

function editOrder(order_id) {
    $('#editOrder').prop("disabled", true);
    $.post("editOrder",{orderId: order_id}
    ).done(function (data) {
        $('#content').html(data)
    }).fail(function () {
        alert("Sorry, order could not be edited")
    }).always(function () {
        $('#editOrder').prop("disabled", false);
    })
}

function addNewDish() {
    $('#addNewDish').prop("disabled", true);
    $.get("addNewDish"
    ).done(function (data) {
        $('#in-content').html(data)
    }).fail(function () {
        alert("Sorry, new dish could not be added")
    }).always(function () {
        $('#addNewDish').prop("disabled", false);
    })
}

function addNewDishCategory() {
    $('#addNewDishCategory').prop("disabled", true);
    $.get("addNewDishCategory"
    ).done(function (data) {
        $('#in-content').html(data)
    }).fail(function () {
        alert("Sorry, new dish could not be added")
    }).always(function () {
        $('#addNewDishCategory').prop("disabled", false);
    })
}

function editDish(dish_id) {
    $('#editDish').prop("disabled", true);
    $.post("editDish",{dishId: dish_id}
    ).done(function (data) {
        $('#in-content').html(data)
    }).fail(function () {
        alert("Sorry, new dish could not be edited")
    }).always(function () {
        $('#editDish').prop("disabled", false);
    })
}

function editDishCategory(dishCategory_id) {
    $('#editDishCategory').prop("disabled", true);
    $.post("editDishCategory",{dishCategoryId: dishCategory_id}
    ).done(function (data) {
        $('#in-content').html(data)
    }).fail(function () {
        alert("Sorry, new dish category could not be edited")
    }).always(function () {
        $('#editDishCategory').prop("disabled", false);
    })
}

function saveOrUpdateDish() {
       var dish_id = $('#idDish').val();
       var dish_name = $('#nameDish').val();
       var dish_description = $('#descriptionDish').val();
       var dish_price = $('#priceDish').val();
       var dish_category = $('#selectDishCategory').val();

    if(dish_name ==="" || dish_description ==="" || dish_price ===""){
        alert("The fields mustn't be empty!")
    }else {
        $('#butSaveDish').prop("disabled", true);
        $('#butUpdateDish').prop("disabled", true);
        $.post("saveOrUpdateDish", {
                dishId: dish_id,
                name: dish_name,
                description: dish_description,
                price: dish_price,
                dishCategory_id: dish_category
            }
        ).done(function (data) {
            $('#content').html(data)
        }).fail(function () {
            alert("Sorry, dish hasn't been saved or updated")
        }).always(function () {
            $('#butSaveDish').prop("disabled", false);
            $('#butUpdateDish').prop("disabled", false);
        })
    }
}

function saveOrUpdateDishCategory() {
    var dishCategory_id = $('#dishCategoryId').val();
    var dishCategory_name = $('#nameDishCategory').val();

    if(dishCategory_name ===""){
        alert("The fields mustn't be empty!")
    }else {
        $('#butSaveCategory').prop("disabled", true);
        $('#butUpdateCategory').prop("disabled", true);
        $.post("saveOrUpdateDishCategory", {dishCategoryId: dishCategory_id, name: dishCategory_name}
        ).done(function (data) {
            $('#content').html(data)
        }).fail(function () {
            alert("Sorry, dish category hasn't been saved or updated")
        }).always(function () {
            $('#butSaveCategory').prop("disabled", false);
            $('#butUpdateCategory').prop("disabled", false);
        })
    }
}

function deleteMenuDish(dish_id) {
    var conf = confirm("Do you really want to delete dish with id:"+dish_id+"?");
    if(conf) {
        $('#deleteMenuDish').prop("disabled", true);
        $.post("deleteMenuDish", {dishId: dish_id}
        ).done(function (data) {
            $('#content').html(data)
        }).fail(function () {
            alert("Sorry, dish hasn't been deleted")
        }).always(function () {
            $('#deleteMenuDish').prop("disabled", false);
        })
    }
}

function deleteDishCategory(dishCategory_id) {
        var conf = confirm("Do you really want to delete dish category with id="+dishCategory_id);
    if(conf){
            $('#deleteDishCategory').prop("disabled", true);
            $.post("deleteDishCategory",{dishCategoryId: dishCategory_id}
            ).done(function (data) {
                $('#content').html(data);
            }).fail(function () {
                alert("Dish category hasn't been deleted");
            }).always(function () {
                $('#deleteDishCategory').prop("disabled", false)
            })
    }
}

function saveOrUpdateGroup() {
    var group_id = $('#idGroup').val();
    var group_name = $('#nameGroup').val();
    var group_description = $('#descriptionGroup').val();

    if(group_name ==="" || group_description ===""){
        alert("The fields mustn't be empty!")
    }else {
        $('#butSaveGroup').prop("disabled", true);
        $('#butUpdateGroup').prop("disabled", true);
        $.post("saveOrUpdateGroup", {groupId: group_id, name: group_name, description: group_description}
        ).done(function (data) {
            $('#content').html(data)
        }).fail(function () {
            alert("Sorry, group hasn't been saved or updated")
        }).always(function () {
            $('#butSaveGroup').prop("disabled", false);
            $('#butUpdateGroup').prop("disabled", false);
        })
    }
}

function editGroup(groupId) {
    $('#editGroup').prop("disabled", true);
    $.post("editGroup",{groupId: groupId}
    ).done(function (data) {
        $('#in-content').html(data)
    }).fail(function () {
        alert("Sorry, new group could not be edited")
    }).always(function () {
        $('#editGroup').prop("disabled", false);
    })
}

function deleteGroup(group_id) {
    var conf = confirm("Do you really want to delete group with id="+group_id);
    if(conf){
        $('#deleteGroup').prop("disabled", true);
        $.post("deleteGroup",{groupId: group_id}
        ).done(function (data) {
            $('#content').html(data);
        }).fail(function () {
            alert("Group hasn't been deleted");
        }).always(function () {
            $('#deleteGroup').prop("disabled", false)
        })
    }
}

function joinToGroup(groupId) {
    $('#joinToGroup').prop("disabled", true);
    $.post("joinToGroup",{groupId: groupId}
    ).done(function (data) {
        alert("Your request to join to the group with id="+groupId+" has been successfully sent to owner. Wait for his(her) decision. Please be checking your state by group. ");
        $('#content').html(data)
    }).fail(function () {
        alert("Sorry, your join request to group with id="+groupId+" hasn't been sent")
    }).always(function () {
        $('#joinToGroup').prop("disabled", false);
    })
}

function acceptJoinRequest(joinRequest_id) {
    var conf = confirm("Do you really want to accept join request with id="+joinRequest_id);
    if(conf){
        $('#acceptJoinRequest').prop("disabled", true);
        $.post("acceptJoinRequest",{joinRequestId: joinRequest_id}
        ).done(function (data) {
            $('#content').html(data);
        }).fail(function () {
            alert("Join request hasn't been accepted");
        }).always(function () {
            $('#acceptJoinRequest').prop("disabled", false)
        })
    }
}

function rejectJoinRequest(joinRequest_id) {
    var conf = confirm("Do you really want to reject join request with id="+joinRequest_id);
    if(conf){
        $('#rejectJoinRequest').prop("disabled", true);
        $.post("rejectJoinRequest",{joinRequestId: joinRequest_id}
        ).done(function (data) {
            $('#content').html(data);
        }).fail(function () {
            alert("Join request hasn't been rejected");
        }).always(function () {
            $('#rejectJoinRequest').prop("disabled", false)
        })
    }
}

function quitGroup(group_id) {
    var conf = confirm("Do you really want to quit group with id="+group_id);
    if(conf){
        $('#quitGroup').prop("disabled", true);
        $.post("quitGroup",{groupId: group_id}
        ).done(function (data) {
            $('#content').html(data);
        }).fail(function () {
            alert("The group hasn't been left");
        }).always(function () {
            $('#quitGroup').prop("disabled", false)
        })
    }
}