using System;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace ConsoleClient
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("=== МНОГОПОЛЬЗОВАТЕЛЬСКИЙ КЛИЕНТ ===");
            Console.Write("Введите ваше имя: ");
            string userName = Console.ReadLine();

            TcpClient client = new TcpClient("127.0.0.1", 8889);
            NetworkStream stream = client.GetStream();

            byte[] nameData = Encoding.UTF8.GetBytes(userName);
            stream.Write(nameData, 0, nameData.Length);

            Console.WriteLine("Подключено! Введите сообщения (или 'exit' для выхода):\n");

            Thread receiveThread = new Thread(() => ReceiveMessages(stream));
            receiveThread.Start();

            while (true)
            {
                string message = Console.ReadLine();
                if (message.ToLower() == "exit") break;
                byte[] data = Encoding.UTF8.GetBytes(message);
                stream.Write(data, 0, data.Length);
            }

            stream.Close();
            client.Close();
        }

        static void ReceiveMessages(NetworkStream stream)
        {
            byte[] buffer = new byte[4096];
            while (true)
            {
                try
                {
                    int bytesRead = stream.Read(buffer, 0, buffer.Length);
                    if (bytesRead == 0) break;
                    string message = Encoding.UTF8.GetString(buffer, 0, bytesRead);
                    Console.WriteLine(message);
                }
                catch { break; }
            }
        }
    }
}