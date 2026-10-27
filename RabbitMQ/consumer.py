from pika import BlockingConnection, ConnectionParameters, PlainCredentials


STATUS = {
    'ok': '\033[92m',
    'fail': '\033[91m',
    'info': '\033[90m',
    'warn': '\033[93m',
    'end': '\033[0m'
}

def cwrapper(text: str, status: str) -> str:
    return STATUS[status] + text + STATUS['end']


class iterator:
    i = 0
    
    @staticmethod
    def next():
        iterator.i += 1
        return iterator.i


def consume():
    # Настройка подключения
    credentials = PlainCredentials('admin', 'admin')
    connection = BlockingConnection(ConnectionParameters('localhost', 5672, '/', credentials))
    channel = connection.channel()

    # Создаем очередь (если еще не существует)
    channel.queue_declare(queue='test_queue')

    # Функция для обработки полученного сообщения
    def callback(ch, method, properties, body):
        print(f"< {iterator.next()}. Получено сообщение от {cwrapper(properties.user_id, 'warn')}:\n< '{cwrapper(body.decode(), 'ok')}'")

    # Настраиваем получение сообщений из очереди
    channel.basic_consume(queue='test_queue',
                        on_message_callback=callback,
                        auto_ack=True
                        )

    print(cwrapper("< Ожидание сообщений ...", 'warn'))
    channel.start_consuming()


if __name__ == "__main__":
    consume()
