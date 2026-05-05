using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Imaging;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace ImageServer
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("=== СЕРВЕР ОБРАБОТКИ ИЗОБРАЖЕНИЙ ===");
            Console.WriteLine("Запуск сервера на порту 8890...");

            TcpListener server = new TcpListener(IPAddress.Any, 8890);
            server.Start();
            Console.WriteLine("Сервер запущен. Ожидание подключений...\n");

            while (true)
            {
                TcpClient client = server.AcceptTcpClient();
                Console.WriteLine("Клиент подключён");
                Thread clientThread = new Thread(HandleClient);
                clientThread.Start(client);
            }
        }

        static void HandleClient(object obj)
        {
            TcpClient client = (TcpClient)obj;
            NetworkStream stream = client.GetStream();

            try
            {
                byte[] sizeBuffer = new byte[8];
                stream.Read(sizeBuffer, 0, 8);
                long imageSize = BitConverter.ToInt64(sizeBuffer, 0);
                Console.WriteLine($"Получен размер файла: {imageSize} байт");

                byte[] imageBuffer = new byte[imageSize];
                int bytesRead = 0;
                while (bytesRead < imageSize)
                {
                    bytesRead += stream.Read(imageBuffer, bytesRead, (int)(imageSize - bytesRead));
                }
                Console.WriteLine($"Изображение получено. Размер: {imageBuffer.Length} байт");

                byte[] resizedImage = ResizeImage(imageBuffer);
                Console.WriteLine($"Изображение сжато. Новый размер: {resizedImage.Length} байт");

                byte[] responseSize = BitConverter.GetBytes(resizedImage.Length);
                stream.Write(responseSize, 0, 8);
                stream.Write(resizedImage, 0, resizedImage.Length);

                Console.WriteLine("Ответ отправлен клиенту\n");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Ошибка: {ex.Message}");
            }
            finally
            {
                client.Close();
            }
        }

        static byte[] ResizeImage(byte[] imageBuffer)
        {
            using (var inputStream = new MemoryStream(imageBuffer))
            using (var originalImage = new Bitmap(inputStream))
            {
                int newWidth = originalImage.Width / 2;
                int newHeight = originalImage.Height / 2;

                using (var resizedImage = new Bitmap(newWidth, newHeight))
                using (var graphics = Graphics.FromImage(resizedImage))
                {
                    graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
                    graphics.DrawImage(originalImage, 0, 0, newWidth, newHeight);
                }

                using (var outputStream = new MemoryStream())
                {
                    resizedImage.Save(outputStream, ImageFormat.Jpeg);
                    return outputStream.ToArray();
                }
            }
        }
    }
}