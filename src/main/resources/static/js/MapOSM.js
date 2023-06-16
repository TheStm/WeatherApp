function loadMap(lat, lng)
{
	let map;
	let osm_layer;
	let cities_layer;

	osm_layer = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
		minZoom: DEFAULT_MIN_ZOOM,
		maxZoom: DEFAULT_MAX_ZOOM,
		attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a>'
	});
	
	cities_layer = L.OWM.current(
	{
		intervall: 15,
		imageLoadingUrl: 'owmloading.gif',
		lang: 'pl',
		minZoom: 5,
		appId: API_KEY_OPENWEATHER
	});

	map = L.map('map', {
		center: new L.LatLng(lat, lng),
		zoom: DEFAULT_ZOOM,
		layers: [osm_layer, cities_layer]
	});
}