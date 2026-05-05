using System;
using System.IO;
using System.Net.Sockets;

namespace ImageClient
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("=== КЛИЕНТ ОТПРАВКИ ИЗОБРАЖЕНИЙ ===");
            Console.Write("Введите путь к файлу изображения: ");
            string filePath = Console.ReadLine();

            if (!File.Exists(filePath))
            {
                Console.WriteLine("Файл не найден!");
                return;
            }

            byte[] imageData = File.ReadAllBytes(filePath);
            Console.WriteLine($"Файл загружен. Размер: {imageData.Length} байт");

            TcpClient client = new TcpClient("127.0.0.1", 8890);
            NetworkStream stream = client.GetStream();

            byte[] sizeData = BitConverter.GetBytes(imageData.Length);
            stream.Write(sizeData, 0, 8);
            stream.Write(imageData, 0, imageData.Length);

            Console.WriteLine("Изображение отправлено. Ожидание ответа...");

            byte[] responseSizeBuffer = new byte[8];
            stream.Read(responseSizeBuffer, 0, 8);
            long responseSize = BitConverter.ToInt64(responseSizeBuffer, 0);

            byte[] responseData = new byte[responseSize];
            int bytesRead = 0;
            while (bytesRead < responseSize)
                bytesRead += stream.Read(responseData, bytesRead, (int)(responseSize - bytesRead));

            string outputPath = Path.Combine(Path.GetDirectoryName(filePath), "resized_" + Path.GetFileName(filePath));
            File.WriteAllBytes(outputPath, responseData);

            Console.WriteLine($"Сжатое изображение сохранено: {outputPath}");
            Console.WriteLine($"Размер сжатого изображения: {responseData.Length} байт");

            stream.Close();
            client.Close();
            Console.WriteLine("Готово!");
        }
    }
}