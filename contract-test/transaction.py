import requests

# URL do endpoint
url = 'http://localhost:8080/v1/transaction/'

# Dados que você deseja enviar na requisição POST
# Neste caso, não estamos enviando dados, mas você pode adicionar conforme necessário
data = {
  "account": 1,
  "totalAmount": 50.00,
  "mcc": "541",
  "merchant": "UBER EATS SAO PAULO BR"
}




response = requests.post(url, json=data)

# Imprimindo o status code da resposta
print('Status Code:', response.status_code)

print('Response Body:', response.text)