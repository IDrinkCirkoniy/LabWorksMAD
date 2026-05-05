using System;
using System.Net.Sockets;
using System.Text;

namespace Client5_1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("=== КЛИЕНТУРА ===");
            Console.WriteLine("Подключение к серверу...");

            TcpClient client = new TcpClient("127.0.0.1", 8888);
            NetworkStream stream = client.GetStream();

            Console.WriteLine("Подключён к серверу. Начинаем чтение данных...\n");

            byte[] buffer = new byte[1024];

            while (true)
            {
                int bytesRead = stream.Read(buffer, 0, buffer.Length);
                if (bytesRead == 0) break;
                string message = Encoding.UTF8.GetString(buffer, 0, bytesRead);
                Console.WriteLine($"Получено: {message}");
            }
        }
    }
}