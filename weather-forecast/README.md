# Weather Forecast App
Find the current weather at a location.

## Use Case
Help travellers decide what kind of clothes they should pack.

## Features
App should be accessible through a terminal.
Should allow user to enter the name of a city.
For any city, user should be able to see :
* Average Temperature for each day. For next 5 days.
* How is the sky going to be on each day? e.g., Sunny, Cloudy, Rainy, etc.

## APIs required
* [Nominatim](https://nominatim.openstreetmap.org/search?q=bangalore&format=jsonv2). [Docs](https://nominatim.org/release-docs/develop/api/Overview/)
* [Places DB](https://www.geoapify.com/data-share/localities/in.zip). [Docs](https://www.geoapify.com/download-all-the-cities-towns-villages/). The data for this is already in the `./data` folder.
* [Open-Meteo](https://api.open-meteo.com/v1/forecast?latitude=12.9719&longitude=77.5937&daily=weather_code,temperature_2m_max,temperature_2m_min&timezone=Asia%2FCalcutta&temporal_resolution=hourly_6). [Docs](https://open-meteo.com/en/docs)
* Weather codes [reference](https://artefacts.ceda.ac.uk/badc_datadocs/surface/code.html). We chose automated readings table for the workshop.

## Tracker
- [ ] Get city name from user.
- [ ] Fetch coordinates for the city from the dataset. Or error message if not found.
- [ ] Fetch weather for the location.
- [ ] Pretty print the information.
- [ ] Cache API results.