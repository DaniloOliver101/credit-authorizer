import requests

# Substitua YOUR_ACCOUNT_ID pelo ID da conta desejada
account_id = '1'

url = f'http://localhost:8080/v1/account/balance/{account_id}'

response = requests.get(url)

# Verificando se a resposta foi bem-sucedida
if response.status_code == 200:
    print('Saldo da conta:', response.json())
else:
    print('Erro ao buscar o saldo. Status Code:', response.status_code)