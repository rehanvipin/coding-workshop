import requests
import pandas as pd

# Read cities dataset
data = pd.read_json('../data/place-city.ndjson', lines=True)
# print(data)

print("Weather Forecast App")
print("Find the weather in a city of your choice.")
city_name = input("Please enter the name of the city: ").strip().lower()
data['searchname'] = data['name'].str.lower()
data['searchname'] = data['searchname'].str.strip()
# rows = data.loc[data['name'].str.strip().str.lower().str.contains(city_name)]
print(rows)