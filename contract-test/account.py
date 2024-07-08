import requests

url = 'http://localhost:8080/v1/account/'


data = {
  
  "foodBalance": 500.0,
  "mealBalance": 500.0,
  "cashBalance": 1000.0
}



response = requests.post(url, json=data)

# Imprimindo o status code da resposta
print('Status Code:', response.status_code)

print('Response Body:', response.text)