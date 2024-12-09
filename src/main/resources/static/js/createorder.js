$(document).ready(function () {
    getProductsRequest();
    $('.continue').on("click", function () {
        window.location.href = '/'
    })
    $('.close').on("click", function () {
        closeAndRefreshModal()
    })
    $('#create-order').on("click", function () {
        createOrderRequest()
    })

});

function getProductsRequest() {
    $.ajax({
        type: 'GET',
        url: `/api/products`,
    }).done(function (data) {
        parseAndInsertProducts(data);
    })
}

function parseAndInsertProducts(data) {
    var avalibleProducts = $('.avalible_products')
    data.forEach(product => {
        var productWrapper = $('<div>').addClass('flex column avalible-product-wrapper')
        productWrapper.attr('data-article', product.article)
        var productName = $('<div>').addClass('avalible-product-name').text(product.name)
        var productPrice = $('<div>').addClass('avalible-product-price-wrapper').append($('<span>').addClass('text-price').text('Цена:')).append($('<span>').addClass('amount-price').text(product.price))
        var addButton = $('<div>').addClass('add-product-btn').text('Добавить')
        avalibleProducts.append(productWrapper.append(productName).append(productPrice).append(addButton))
        addButton.on("click", function () {
            clickBtnActionForProducts($(this), product.price)
        })
    });
}

function clickBtnActionForProducts(btn, price) {
    var avalibleProducts = $('.products')
    var exist = avalibleProducts.find(`.checkout-row[data-article='${btn.parent().data('article')}']`)
    if (exist.length) {
        exist.find('input').val(parseInt(exist.find('input').val()) + 1)
        $('.total-amount').text(parseInt($('.total-amount').text()) + price)
    } else {
        var row = $('<div>').addClass('flex checkout-row').attr('data-article', btn.parent().data('article'))
        var productName = $('<div>').addClass('cell product-name').text(btn.parent().find('.avalible-product-name').text())
        var productPrice = $('<div>').addClass('cell product-price').text(btn.parent().find('.amount-price').text())
        var amountWrapper = $('<div>').addClass('cell flex product-amount-wrapper')
        var inputAmount = $('<input>').attr('type', 'number').attr('id', 'product-amount').attr('min', 1).addClass('product-amount').val(1)
        var deleteBtn = $('<div>').addClass('delete-product-btn')
        avalibleProducts.append(row.append(productName).append(productPrice).append(amountWrapper.append(inputAmount).append(deleteBtn)))
        inputAmountActionListner(inputAmount)
        deleteBtnActionListner(deleteBtn)
        $('.total-amount').text(parseInt($('.total-amount').text()) + price)
    }
}

function inputAmountActionListner(input) {
    input.on("change", function () {
        if (parseInt($(this).val()) <= 0) {
            $(this).val(1)
        }
        countTotalAmount()
    });
}

function deleteBtnActionListner(deleteBtn) {
    deleteBtn.on("click", function () {
        $(this).parents('.checkout-row').remove()
        countTotalAmount()
    })

}

function countTotalAmount() {
    var avalibleProducts = $('.products .checkout-row')
    var sum = 0
    avalibleProducts.each(function () {
        sum += parseInt($(this).find('.product-price').text()) * parseInt($(this).find('.product-amount').val())

    });
    $('.total-amount').text(sum)
}

function createOrderRequest() {
    if ($('#customer_name').val().length < 2 || $('#customer_name').val() == ' ') {
        alert("Введите ФИО")
        return
    }
    if ($('#delivery_type').val() != 'Самовывоз' && ($('#delivery_address').val().length < 2 || $('#delivery_address').val() == ' ')) {
        alert("Введите адрес")
        return
    }
    if ($('.products .checkout-row').length == 0) {
        alert("Добавьте хотя бы один товар")
        return
    }
    var data = {
        "customerName": $('#customer_name').val(),
        "deliveryAddress": $('#delivery_address').val(),
        "paymentType": $('#payment_type').val(),
        "deliveryType": $('#delivery_type').val(),
    }
    data.detailOrders = []
    var avalibleProducts = $('.products .checkout-row')
    avalibleProducts.each(function () {
        data.detailOrders.push({ "productAmount": $(this).find('.product-amount').val(), "productArticle": $(this).data('article') })
    });
    $.ajax({
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
        url: '/api/orders',
    }).done(function (order) {
        turnOnModal('success', order.orderNumber)
    }).fail(function () {
        turnOnModal('fail')
        console.log('fail');
    });

}

function turnOnModal(action, orderNo) {
    if (action == 'success') {
        $('.order-number').text(`ВАШ ЗАКАЗ №${orderNo} УСПЕШНО ОФОРМЛЕН!`)
        $('.created-order-successful').removeClass('hidediv')
        $('.modal').removeClass('hidediv')
    }
    if (action == 'fail') {
        $('.created-order-failed').removeClass('hidediv')
        $('.modal').removeClass('hidediv')
    }

}

function closeAndRefreshModal() {
    $('.order-number').text('')
    $('.created-order-successful').addClass('hidediv')
    $('.created-order-failed').addClass('hidediv')
    $('.modal').addClass('hidediv')
}