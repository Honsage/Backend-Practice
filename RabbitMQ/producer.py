import time
from pika import BlockingConnection, ConnectionParameters, PlainCredentials, BasicProperties


STATUS = {
    'ok': '\033[92m',
    'fail': '\033[91m',
    'info': '\033[90m',
    'end': '\033[0m'
}

def cwrapper(text: str, status: str) -> str:
    return STATUS[status] + text + STATUS['end']


def produce():
    # Настройка подключения
    credentials = PlainCredentials('admin', 'admin') # Логин и пароль от брокера
    properties = BasicProperties(user_id='admin');
    connection = BlockingConnection(ConnectionParameters('localhost', 5672, '/', credentials))
    channel = connection.channel()

    # Создаем очередь (если еще не существует)
    channel.queue_declare(queue='test_queue')

    print(cwrapper("< Введите команду 'stop' чтобы завершить передачу сообщений", 'info'))
    while True:
        message = input(cwrapper("< Введите сообщение для передачи:", 'info') + "\n> ")
        if message == "stop":
            break
        channel.basic_publish(exchange='',
                            properties=properties,
                            routing_key='test_queue',
                            body=message
                            )
        print(cwrapper('< Сообщение отправлено успешно!', 'ok'))
        time.sleep(1)

    # Закрываем соединение
    connection.close()


if __name__ == "__main__":
    produce()
