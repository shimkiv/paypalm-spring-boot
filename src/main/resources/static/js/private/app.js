"use strict";

$(function() {
    let accountEndpoint =
        "/account";
    let paymentForm =
        $("#paymentForm");
    let paymentProcDialog =
        $("#payment-processing-dialog");
    let paymentProcessingInfoId =
        $("#paymentProcessingInfo");
    let paymentProcessingDialogCloseButton =
        $("#paymentProcessingDialogCloseButton");

    $("#settings-dialog").on("shown.bs.modal", function () {
        $("#firstName").focus();
    });

    $("#returnBack").on("click", function() {
        window.location.replace(accountEndpoint);
    });

    $("#clearShoppingCartButton").on("click", function() {
        if ($(this).not(":disabled")) {
            $.ajax({
                method: "POST",
                url: "/account/clear-shopping-cart"
            }).always(function() {
                window.location.replace(accountEndpoint);
            });
        }
    });

    $("[type='checkbox']").on("click", function() {
        $.ajax({
            method: "POST",
            url: "/account/update-shopping-cart",
            data: "productId=" + $(this).attr("value") + "&status=" + $(this).is(":checked")
        }).always(function() {
            window.location.replace(accountEndpoint);
        });
    });

    paymentProcDialog.modal({
        backdrop: "static",
        keyboard: false,
        show: false
    });

    paymentForm.on("submit", function(e) {
        e.preventDefault();

        paymentProcessingInfoId.empty();

        $.ajax({
            method: "POST",
            url: paymentForm.attr("action"),
            data: paymentForm.serialize()
        }).always(function(json, textStatus) {
            let color = "";
            let text = "error";
            let message = "";

            if (textStatus === "success") {
                if (json.state === "approved") {
                    color = "green";
                } else {
                    color = "red";
                }

                if (json.state !== undefined) {
                    text = json.state;
                }

                // noinspection JSUnresolvedVariable
                if (json.errorResponse !== undefined &&
                    json.errorResponse != null &&
                    json.errorResponse.message != null) {
                    // noinspection JSUnresolvedVariable
                    message = '<p style="color: gray; font-size: 15px;">' + json.errorResponse.message + '</p>';
                }
            } else {
                color = "red";
            }

            paymentProcessingInfoId.append('<div class="centered-text"><h1 style="padding-bottom: 20px; color: ' + color + ';">' + text.toUpperCase() + '</h1>' + message + '</div>');
            paymentProcDialog.modal("show");
        });

        return false;
    });

    paymentProcessingDialogCloseButton.on("click", function() {
        window.location.replace(accountEndpoint);
    });
});
