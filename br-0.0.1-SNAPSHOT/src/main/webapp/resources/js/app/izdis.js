PrimeFaces.locales['lt'] = {
                 closeText: 'Uždaryti',
                 prevText: 'Kitas',
                 nextText: 'Ankstesnis',
                 currentText: 'Į pradžią',
                 monthNames: ['sausis', 'vasaris', 'kovas', 'balandis', 'gegužė', 'birželis', 'liepa', 'rugpjūtis', 'rugsėjis', 'spalis', 'lapkritis', 'gruodis' ],
                 monthNamesShort: ['sau', 'vas', 'kov', 'bal', 'geg', 'nir', 'lie', 'rgp', 'rgs', 'spa', 'lap', 'gru' ],
                 dayNames: ['sekmadienis', 'pirmadienis', 'antradienis', 'trečiadienis', 'ketvirtadienis', 'penktadienis', 'šeštadienis'],
                 dayNamesShort: ['sekm', 'pirm', 'antr', 'treč', 'ketv', 'penkt', 'šešt'],
                 dayNamesMin: ['s', 'p', 'a', 't ', 'k', 'p ', 'š'],
                 weekHeader: 'savaitė',
                 firstDay: 1,
                 isRTL: false,
                 showMonthAfterYear: true,
                 yearSuffix:'',
                 timeOnlyTitle: 'Tik laikas',
                 timeText: 'Laikas',
                 hourText: 'val.',
                 minuteText: 'min.',
                 secondText: 'sek.',
                 currentText: 'Dabar',
                 ampm: false,
                 month: 'mėnuo',
                 week: 'savaitė',
                 day: 'diena',
                 allDayText: 'visą dieną'
             };


function handleGoBackRequest(xhr, status, args) {
	if (args.validationFailed || !args.canLooseState) {
		PF('confirmDialog').show();
	} else {
		history.go(-2);
		return false;
	}
}