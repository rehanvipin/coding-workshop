import requests
import json

with open('../data/place-city.ndjson') as datafile:
    lines = datafile.readlines()
    places = map(lambda line: json.loads(line), lines)

with open('../data/weather-codes.json') as weathercodesfile:
    weather_codes = json.load(weathercodesfile)

def find_place(place_name: str):
    search_name = place_name.lower().strip()
    for place in places:
        if not 'name' in place or place['name'] is None:
            continue
        if search_name in place['name'].lower():
            return place
        if not 'other_names' in place or place['other_names'] is None:
            continue
        for other_name in place['other_names']:
            if search_name in other_name.lower():
                return place
    return None

print("Weather Forecast App")
print("Find the weather in a city of your choice.")
city_name = input("Please enter the name of the city: ").strip().lower()
place = find_place(city_name)
if place is None:
    print("Could not find the place")
    exit(1)
if 'location' not in place:
    print("Could not find location of place")
    exit(1)
x_coord, y_coord = place['location']

weather_api = f'https://api.open-meteo.com/v1/forecast?latitude={x_coord}&longitude={y_coord}&daily=weather_code,temperature_2m_max,temperature_2m_min&timezone=Asia%2FCalcutta&temporal_resolution=hourly_6'

weather_api_resp = requests.get(weather_api)
weather_data = json.loads(weather_api_resp.text)

print('')
for index, day in enumerate(weather_data['daily']['time']):
    print('Forecast for day:', day)
    weather_code = weather_data['daily']['weather_code'][index]
    if str(weather_code) in weather_codes:
        print('Atmosphere:', weather_codes[str(weather_code)]['description'])
    else:
        print('Unknown atmosphere')
    print('Min Temperature:', weather_data['daily']['temperature_2m_min'][index])
    print('Max Temperature:', weather_data['daily']['temperature_2m_max'][index])
    print('')