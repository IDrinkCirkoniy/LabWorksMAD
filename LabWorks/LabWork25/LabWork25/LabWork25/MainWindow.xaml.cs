using System.Windows;

namespace LabWork25
{
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void OnButtonClick(object sender, RoutedEventArgs e)
        {
            if (sender is CustomButton button)
            {
                ResultLabel.Text = $"Нажата кнопка: {button.Text}";
            }
        }
    }
}