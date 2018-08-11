'use strict';

$(document).ready(function() {
    $(document)
        .ajaxStart(function() {
            $.LoadingOverlay("show");

            $(".btn").attr("disabled", true);
        })
        .ajaxStop(function() {
            $.LoadingOverlay("hide");

            $(".btn").removeAttr("disabled");
        })
        .ajaxError(function() {
            $.LoadingOverlay("hide");

            $(".btn").removeAttr("disabled");
        });

    $.LoadingOverlaySetup({
        background: "rgba(69, 70, 76, 0.6)",
        imageAnimation: "1.0s fadein",
        imageColor: "#e5e3dc"
    });
});