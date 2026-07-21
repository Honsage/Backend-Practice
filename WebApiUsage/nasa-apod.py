import os
import requests
import argparse
import webbrowser
from dotenv import load_dotenv


class NasaApodApi:
    def __init__(self):
        load_dotenv()
        self.api_key = os.getenv('NASA_API_KEY')
        self.base_url = 'https://api.nasa.gov/planetary/apod'

    def get(self, date=None):
        params = {'api_key': self.api_key}
        if date:
            params['date'] = date
        
        try:
            response = requests.get(self.base_url, params=params)
            response.raise_for_status()
            return response.json()
        except requests.exceptions.RequestException as e:
            print(f"Some errors were occured: {e}")
            return None


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('-d', '--date')
    args = parser.parse_args()

    api = NasaApodApi()
    if response := api.get(args.date):
        print(f"NASA API response is: {response}")
        webbrowser.open(response['url'])


if __name__ == "__main__":
    main()