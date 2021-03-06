
define(['jquery'], function ($) {
	return {
		Accordion: function(el, multiple) {
		this.el = el || {};
		this.multiple = multiple || false;

		// Variables privadas
		var links = this.el.find('.link');
		// Evento
		links.on('click', {el: this.el, multiple: this.multiple}, this.dropDown);

	},
		dropDown: function(e) {
			var $el = e.data.el, $this = $(this), $next = $this.next();

			$next.slideToggle();
			$this.parent().toggleClass('open');

			if (!e.data.multiple) {
				$el.find('.submenu').not($next).slideUp().parent().removeClass('open');
			};
		}
	}


	//var accordion = new Accordion($('#accordion'), false);
});