/**
 * Particleground demo
 * @author Jonathan Nicol - @mrjnicol
 */

$(document).ready(function() {
  $('.particlesBackground').particleground({
    dotColor: '#5cbdaa',
    lineColor: '#5cbdaa'
  });
  $('.intro').css({
	    'margin-top': -($('.intro').height() / 2)
	  });
  $('.intro').css({
	    'margin-left': (($('body').width()-$('.intro').width()) / 2)
	  });
});