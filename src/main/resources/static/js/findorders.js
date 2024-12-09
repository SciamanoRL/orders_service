$(document).ready(function () {
    getAndFillProducts()
    $('.find-by-id-btn').on("click", function () {
        $('.find-by-id-wrapper').toggleClass('hidediv')
        $('.find-by-date-and-amount-wrapper').addClass('hidediv')
        $('.find-by-range-date-wrapper').addClass('hidediv')
    })
    $('.find-by-date-and-amount-btn').on("click", function () {
        $('.find-by-date-and-amount-wrapper').toggleClass('hidediv')
        $('.find-by-id-wrapper').addClass('hidediv')
        $('.find-by-range-date-wrapper').addClass('hidediv')
    })
    $('.find-by-range-date-and-exclude-btn').on("click", function () {
        $('.find-by-range-date-wrapper').toggleClass('hidediv')
        $('.find-by-id-wrapper').addClass('hidediv')
        $('.find-by-date-and-amount-wrapper').addClass('hidediv')
    })
    $('.get-order-btn').on("click", function () {
        getOrderRequest()
    })
    $('.get-orders-by-date-and-amount-btn').on("click", function () {
        getOrdersByDateAndAmountRequest()
    })
    $('.get-orders-by-range-and-exclude-product-btn').on("click", function () {
        getOrdersByRangeDateAndExcludeRequest()
    })
    $('#order-id').on("change", function () {
        if (parseInt($(this).val()) <= 0) {
            $(this).val(1)
        }
    })
    $('.amount-picker').on("change", function () {
        if (parseInt($(this).val()) <= 0) {
            $(this).val(1)
        }
    })

});

function getAndFillProducts() {
    $.ajax({
        type: 'GET',
        url: `/api/products`,
    }).done(function (data) {
        parseAndInsertProducts(data);
    })
}

function getOrderRequest() {
    $.ajax({
        type: 'GET',
        url: `/api/orders/${$('#order-id').val()}`,
    }).done(function (data) {
        removeOrders()
        parseAndFillOrder(data, $('.find-by-id-wrapper'))
    })
}

function getOrdersByDateAndAmountRequest() {
    $.ajax({
        type: 'GET',
        url: `/api/orders?date=${$('.date-picker-wrapper > input').val()}&amount=${$('.amount-picker-wrapper > input').val()}`,
    }).done(function (data) {
        data.forEach(order => {
            parseAndFillOrder(order, $('.find-by-date-and-amount-wrapper'))
        });
        
    })
}

function getOrdersByRangeDateAndExcludeRequest() {
    $.ajax({
        type: 'GET',
        url: `/api/orders?date_from=${$('.date-from-picker-wrapper > input').val()}&date_to=${$('.date-to-picker-wrapper > input').val()}&article=${$('#exclude-product').val()}`,
    }).done(function (data) {
        removeOrders()
        data.forEach(order => {
            parseAndFillOrder(order, $('.find-by-range-date-wrapper'))
        });
        
    })
}

function parseAndFillOrder(order, place) {
    var orderWrapper = $('<div>').addClass('flex column order-wrapper')
    var orderInfoRow = $('<div>').addClass('flex order-row').append($('<div>').addClass('order-number-info').text(`Заказ №${order.orderNumber} от ${new Date(order.orderDate).toLocaleDateString()}`))
    var customerSpanText = $('<span>').text('Заказчик:')
    var customerSpanName = $('<span>').addClass('customer-name').text(order.customerName)
    var customerSpanAddressText = $('<span>').text('Адрес доставки:')
    var customerSpanAddress = $('<span>').addClass('delivery-address').text(order.deliveryAddress)
    var customerInfoRow = $('<div>').addClass('flex order-row').append($('<div>').append(customerSpanText).append(customerSpanName)).append($('<div>').append(customerSpanAddressText).append(customerSpanAddress))
    var customerSpanPaymentText = $('<span>').text('Оплата:')
    var customerSpanPayment = $('<span>').addClass('payment-type').text(order.paymentType)
    var customerSpanDeliveryText = $('<span>').text('Тип доставки:') 
    var customerSpanDelivery = $('<span>').addClass('delivery-address').text(order.deliveryType)
    var orderPaymentAndAddressRow = $('<div>').addClass('flex order-row').append($('<div>').append(customerSpanPaymentText).append(customerSpanPayment)).append($('<div>').append(customerSpanDeliveryText).append(customerSpanDelivery))
    var productTableRow = $('<div>').addClass('flex column order-details')
    order.detailOrders.forEach(product => {
        parseAndFillProducts(product, productTableRow)
    });
    orderWrapper.append(orderInfoRow).append(customerInfoRow).append(orderPaymentAndAddressRow).append(productTableRow)
    place.append(orderWrapper)
}

function parseAndFillProducts(product, table) {
    var productRow = $('<div>').addClass('flex product-row')
    var productArticle = $('<div>').addClass('cell product-article').text(product.productArticle)
    var productName = $('<div>').addClass('cell product-name').text(product.productName)
    var productAmount = $('<div>').addClass('cell product-amount').text(product.productAmount)
    var productPrice = $('<div>').addClass('cell product-total-price').text(product.productAmount * product.productPrice)
    productRow.append(productArticle).append(productName).append(productAmount).append(productPrice)
    table.append(productRow)
}

function parseAndInsertProducts(products) {
    var select = $('#exclude-product')
    products.forEach(product => {
        var option = $('<option>').val(product.article).text(product.name)
        select.append(option)
    });
}
function removeOrders() {
    $('.order-wrapper').each(function () {
        $(this).remove()
    });
    
}