function ping(njugador){
	$.ajax({
		type: 'POST',
		url: '/proyecto-limpio-spring/app/ping',
		datatype: 'json',
		data: { jugador: njugador },
		success: function (data) {
			if (data.comando == 0){
				setTimeout(ping(),1000)
			}
		},
	});
}