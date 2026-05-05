using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace ConsoleServer
{
    class Program
    {
        private static List<TcpClient> clients = new List<TcpClient>();
        private static readonly object lockObject = new object();

        static void Main(string[] args)
        {
            Console.WriteLine("=== МНОГОПОЛЬЗОВАТЕЛЬСКИЙ СЕРВЕР ===");
            Console.WriteLine("Запуск сервера на порту 8889...");

            TcpListener server = new TcpListener(IPAddress.Any, 8889);
            server.Start();
            Console.WriteLine("Сервер запущен. Ожидание подключений...\n");

            while (true)
            {
                TcpClient client = server.AcceptTcpClient();
                lock (lockObject) { clients.Add(client); }
                Thread clientThread = new Thread(HandleClient);
                clientThread.Start(client);
            }
        }

        static void HandleClient(object obj)
        {
            TcpClient client = (TcpClient)obj;
            NetworkStream stream = client.GetStream();
            byte[] buffer = new byte[4096];
            string clientName = null;

            try
            {
                while (true)
                {
                    int bytesRead = stream.Read(buffer, 0, buffer.Length);
                    if (bytesRead == 0) break;
                    string message = Encoding.UTF8.GetString(buffer, 0, bytesRead);

                    if (clientName == null)
                    {
                        clientName = message;
                        Console.WriteLine($"[{DateTime.Now:HH:mm:ss dd.MM.yyyy}] {clientName} подключился");
                    }
                    else
                    {
                        Console.WriteLine($"[{DateTime.Now:HH:mm:ss dd.MM.yyyy}] {clientName}: {message}");
                    }
                }
            }
            finally
            {
                lock (lockObject) { clients.Remove(client); }
                if (clientName != null)
                    Console.WriteLine($"[{DateTime.Now:HH:mm:ss dd.MM.yyyy}] {clientName} отключился");
                client.Close();
            }
        }
    }
}