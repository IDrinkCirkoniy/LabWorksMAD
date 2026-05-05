
using System;
using System.Net;
using System.Net.Sockets;
using System.Text;

namespace Server
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("=== СЕРВАК ПРИВАТКА ===");
            Console.WriteLine("Запуск сервера на порту 8888...");

            TcpListener server = new TcpListener(IPAddress.Any, 8888);
            server.Start();

            Console.WriteLine("Сервер запущен. Ожидание подключения...");

            TcpClient client = server.AcceptTcpClient();
            Console.WriteLine("Клиент подключён!");

            NetworkStream stream = client.GetStream();
            int counter = 1;

            while (true)
            {
                string message = $"Сообщение от сервера #{counter}";
                byte[] data = Encoding.UTF8.GetBytes(message);
                stream.Write(data, 0, data.Length);
                Console.WriteLine($"Отправлено: {message}");
                counter++;
                System.Threading.Thread.Sleep(5000);
            }
        }
    }
}