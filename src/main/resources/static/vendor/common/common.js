// Tooltip and Popover
(function($) {
	$('[data-toggle="tooltip"]').tooltip();
	$('[data-toggle="popover"]').popover();
})(jQuery);

// Tabs
$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	$(this).parents('.nav-tabs').find('.active').removeClass('active');
	$(this).parents('.nav-pills').find('.active').removeClass('active');
	$(this).addClass('active').parent().addClass('active');
});

// common scripts - Akila Nilakshi

$('.input-daterange').keydown(function () {
    return false;
});

$('.nav-active').on('click', function () {
    console.log($(this).text());
});
