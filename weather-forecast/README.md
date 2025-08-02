# Weather Forecast App
Find the current weather at a location.

## Use Case
Help travellers decide what kind of clothes they should pack.

## Features
App should be accessible through a terminal.
It should support a limited set of cities.
For any city, user should be able to see :
* Average Temperature for each day. For next 5 days.
* How is the sky going to be on each day? e.g., Sunny, Cloudy, Rainy, etc.

## APIs required
* https://nominatim.openstreetmap.org/search?q=bangalore&format=jsonv2
* https://api.open-meteo.com/v1/forecast?latitude=12.9719&longitude=77.5937&daily=weather_code,temperature_2m_max,temperature_2m_min&timezone=Asia%2FCalcutta&temporal_resolution=hourly_6