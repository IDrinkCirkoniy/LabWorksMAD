using System;
using System.Windows;
using System.Windows.Input;
using System.Windows.Media;

namespace LabWork25
{
    public class CustomButton : FrameworkElement
    {
        private VisualCollection _children;
        private DrawingVisual _visual;

        protected override int VisualChildrenCount => _children.Count;
        protected override Visual GetVisualChild(int index) => _children[index];

        private bool _isHovered;
        private bool _isPressed;
        private double _scale = 1.0;
        private double _targetScale = 1.0;

        public static readonly DependencyProperty TextProperty =
            DependencyProperty.Register(nameof(Text), typeof(string), typeof(CustomButton),
                new FrameworkPropertyMetadata("Button",
                    FrameworkPropertyMetadataOptions.AffectsRender |
                    FrameworkPropertyMetadataOptions.AffectsMeasure));

        public string Text
        {
            get => (string)GetValue(TextProperty);
            set => SetValue(TextProperty, value);
        }

        public static readonly DependencyProperty BackgroundColorProperty =
            DependencyProperty.Register(nameof(BackgroundColor), typeof(Brush), typeof(CustomButton),
                new FrameworkPropertyMetadata(new SolidColorBrush(Colors.DodgerBlue),
                    FrameworkPropertyMetadataOptions.AffectsRender));

        public Brush BackgroundColor
        {
            get => (Brush)GetValue(BackgroundColorProperty);
            set => SetValue(BackgroundColorProperty, value);
        }

        public static readonly DependencyProperty HoverColorProperty =
            DependencyProperty.Register(nameof(HoverColor), typeof(Brush), typeof(CustomButton),
                new FrameworkPropertyMetadata(new SolidColorBrush(Colors.DeepSkyBlue),
                    FrameworkPropertyMetadataOptions.AffectsRender));

        public Brush HoverColor
        {
            get => (Brush)GetValue(HoverColorProperty);
            set => SetValue(HoverColorProperty, value);
        }

        public static readonly DependencyProperty PressedColorProperty =
            DependencyProperty.Register(nameof(PressedColor), typeof(Brush), typeof(CustomButton),
                new FrameworkPropertyMetadata(new SolidColorBrush(Colors.CornflowerBlue),
                    FrameworkPropertyMetadataOptions.AffectsRender));

        public Brush PressedColor
        {
            get => (Brush)GetValue(PressedColorProperty);
            set => SetValue(PressedColorProperty, value);
        }

        public static readonly DependencyProperty TextColorProperty =
            DependencyProperty.Register(nameof(TextColor), typeof(Brush), typeof(CustomButton),
                new FrameworkPropertyMetadata(new SolidColorBrush(Colors.White),
                    FrameworkPropertyMetadataOptions.AffectsRender));

        public Brush TextColor
        {
            get => (Brush)GetValue(TextColorProperty);
            set => SetValue(TextColorProperty, value);
        }

        public static readonly DependencyProperty FontSizeProperty =
            DependencyProperty.Register(nameof(FontSize), typeof(double), typeof(CustomButton),
                new FrameworkPropertyMetadata(20.0,
                    FrameworkPropertyMetadataOptions.AffectsRender |
                    FrameworkPropertyMetadataOptions.AffectsMeasure));

        public double FontSize
        {
            get => (double)GetValue(FontSizeProperty);
            set => SetValue(FontSizeProperty, value);
        }

        public static readonly DependencyProperty CornerRadiusProperty =
            DependencyProperty.Register(nameof(CornerRadius), typeof(double), typeof(CustomButton),
                new FrameworkPropertyMetadata(10.0,
                    FrameworkPropertyMetadataOptions.AffectsRender));

        public double CornerRadius
        {
            get => (double)GetValue(CornerRadiusProperty);
            set => SetValue(CornerRadiusProperty, value);
        }

        public static readonly RoutedEvent ClickEvent =
            EventManager.RegisterRoutedEvent(
                nameof(Click),
                RoutingStrategy.Bubble,
                typeof(RoutedEventHandler),
                typeof(CustomButton));

        public event RoutedEventHandler Click
        {
            add { AddHandler(ClickEvent, value); }
            remove { RemoveHandler(ClickEvent, value); }
        }

        public CustomButton()
        {
            _children = new VisualCollection(this);
            _visual = new DrawingVisual();
            _children.Add(_visual);

            CompositionTarget.Rendering += OnRenderFrame;

            MouseEnter += OnMouseEnter;
            MouseLeave += OnMouseLeave;
            MouseDown += OnMouseDown;
            MouseUp += OnMouseUp;
        }

        private void OnMouseEnter(object sender, MouseEventArgs e)
        {
            _isHovered = true;
            _targetScale = 1.1;
        }

        private void OnMouseLeave(object sender, MouseEventArgs e)
        {
            _isHovered = false;
            _isPressed = false;
            _targetScale = 1.0;
        }

        private void OnMouseDown(object sender, MouseButtonEventArgs e)
        {
            if (e.LeftButton == MouseButtonState.Pressed)
            {
                _isPressed = true;
                _targetScale = 0.95;
                CaptureMouse();
            }
        }

        private void OnMouseUp(object sender, MouseButtonEventArgs e)
        {
            if (_isPressed)
            {
                _isPressed = false;
                _targetScale = _isHovered ? 1.1 : 1.0;
                ReleaseMouseCapture();

                if (IsMouseOver)
                {
                    RaiseEvent(new RoutedEventArgs(ClickEvent, this));
                }
            }
        }

        private void OnRenderFrame(object sender, EventArgs e)
        {
            _scale += (_targetScale - _scale) * 0.15;
            Draw();
        }

        private void Draw()
        {
            using (var dc = _visual.RenderOpen())
            {
                if (ActualWidth <= 0 || ActualHeight <= 0) return;

                var center = new Point(ActualWidth / 2, ActualHeight / 2);
                dc.PushTransform(new ScaleTransform(_scale, _scale, center.X, center.Y));

                Brush backgroundColor;
                if (_isPressed)
                    backgroundColor = PressedColor;
                else if (_isHovered)
                    backgroundColor = HoverColor;
                else
                    backgroundColor = BackgroundColor;

                var rect = new Rect(0, 0, ActualWidth, ActualHeight);
                var radius = CornerRadius;

                var shadowRect = new Rect(3, 3, ActualWidth, ActualHeight);
                var shadowBrush = new SolidColorBrush(Color.FromArgb(80, 0, 0, 0));
                dc.DrawRoundedRectangle(shadowBrush, null, shadowRect, radius, radius);

                dc.DrawRoundedRectangle(backgroundColor, null, rect, radius, radius);

                if (!string.IsNullOrEmpty(Text))
                {
                    var formattedText = new FormattedText(
                        Text,
                        System.Globalization.CultureInfo.CurrentCulture,
                        FlowDirection.LeftToRight,
                        new Typeface("Segoe UI"),
                        FontSize,
                        TextColor,
                        1.25);

                    var textX = (ActualWidth - formattedText.Width) / 2;
                    var textY = (ActualHeight - formattedText.Height) / 2;
                    dc.DrawText(formattedText, new Point(textX, textY));
                }

                dc.Pop();
            }
        }

        protected override Size MeasureOverride(Size availableSize)
        {
            var formattedText = new FormattedText(
                Text ?? "Button",
                System.Globalization.CultureInfo.CurrentCulture,
                FlowDirection.LeftToRight,
                new Typeface("Segoe UI"),
                FontSize,
                Brushes.White,
                1.25);

            double desiredWidth = formattedText.Width + 40;
            double desiredHeight = formattedText.Height + 30;

            if (!double.IsNaN(Width) && Width > 0)
                desiredWidth = Width;

            if (!double.IsNaN(Height) && Height > 0)
                desiredHeight = Height;

            desiredWidth = Math.Min(desiredWidth, availableSize.Width);
            desiredHeight = Math.Min(desiredHeight, availableSize.Height);

            return new Size(desiredWidth, desiredHeight);
        }
    }
}