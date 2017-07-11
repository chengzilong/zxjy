/**
 * Gensee Slidershow
 * Author: Feil.Wang
 * E-mail: 47655235@qq.com
 * Date Modified: 2014.07.01
 */

$(function () {

	var isIE=!!window.ActiveXObject;
	var isIE6=isIE&&!window.XMLHttpRequest;

	$("#slideshowMain").revolution({
		delay: 6500,
		startwidth: 1000,
		startheight: isIE6?261:500,
		hideThumbs: 0,

		thumbWidth: 100,
		thumbHeight: 50,
		thumbAmount: 3,

		navigationType: "bullet",
		navigationArrows: "solo",
		navigationStyle: "",

		touchenabled: "on",
		onHoverStop: "on",

		navigationHAlign: "center",
		navigationVAlign: "bottom",
		navigationHOffset: 0,
		navigationVOffset: 20,

		soloArrowLeftHalign: "left",
		soloArrowLeftValign: "center",
		soloArrowLeftHOffset: 20,
		soloArrowLeftVOffset: 0,

		soloArrowRightHalign: "right",
		soloArrowRightValign: "center",
		soloArrowRightHOffset: 20,
		soloArrowRightVOffset: 0,

		shadow: 0,
		fullWidth: "on",
		fullScreen: "off",

		stopLoop: "off",
		stopAfterLoops: -1,
		stopAtSlide: -1,

		shuffle: "off",

		hideSliderAtLimit: 0,
		hideCaptionAtLimit: 0,
		hideAllCaptionAtLilmit: 0,
		startWithSlide: 0,
		videoJsPath: "",
		fullScreenOffsetContainer: ""

	});

	$("#lcdSlideshowMain").revolution({
		delay: 3000,
		startwidth: 320,
		startheight: 190,
		hideThumbs: 0,

		thumbWidth: 100,
		thumbHeight: 50,
		thumbAmount: 3,

		navigationType: "bullet",
		navigationArrows: "none",
		navigationStyle: "navbar",

		touchenabled: "on",
		onHoverStop: "on",

		navigationHAlign: "center",
		navigationVAlign: "bottom",
		navigationHOffset: 0,
		navigationVOffset: 0,

		soloArrowLeftHalign: "left",
		soloArrowLeftValign: "center",
		soloArrowLeftHOffset: 20,
		soloArrowLeftVOffset: 0,

		soloArrowRightHalign: "right",
		soloArrowRightValign: "center",
		soloArrowRightHOffset: 20,
		soloArrowRightVOffset: 0,

		shadow: 0,
		fullWidth: "off",
		fullScreen: "off",

		stopLoop: "off",
		stopAfterLoops: -1,
		stopAtSlide: -1,

		shuffle: "off",

		hideSliderAtLimit: 0,
		hideCaptionAtLimit: 0,
		hideAllCaptionAtLilmit: 0,
		startWithSlide: 0,
		videoJsPath: "",
		fullScreenOffsetContainer: ""

	});

});