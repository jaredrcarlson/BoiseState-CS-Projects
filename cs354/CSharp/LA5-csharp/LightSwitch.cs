using System;
using System.Windows.Forms;

namespace LA5
{
    // The LightSwitch class uses BinaryCounter and ToggleButton objects
    // to graphically display and test the functionality of these two classes
    public partial class LightSwitch : Form
    {
        private BinaryCounter bcounter;
        private ToggleButton button;
        private int center; // Current center-point of application window

        public LightSwitch()
        {
            bcounter = new BinaryCounter(0); // Begin the counter at 0
            button = new ToggleButton("off","on"); // Create an "Off/On" button

            // Add counter and button to application window
            button.Parent = this;
            bcounter.Parent = this;

            // Keep application window elements centered
            // Adjusts as window is resized
            center = ClientSize.Width / 2;
            button.Left = center - button.Width;
            button.Top = 10;
            bcounter.Left = center + 5;
            bcounter.Top = 15;

            CenterToScreen(); // Center the application window on the display screen

            // Handle Events for "button clicked" and "window size changed"
            button.Click += Button_Click;
            SizeChanged += LightSwitch_SizeChanged; 
        }

        // Repositions elements if window size changes
        private void LightSwitch_SizeChanged(object sender, EventArgs e)
        {
            center = ClientSize.Width / 2;
            button.Left = center - button.Width;
            button.Top = 10;
            bcounter.Left = center + 5;
            bcounter.Top = 15;
        }

        // Toggles button state and increments count
        private void Button_Click(object sender, EventArgs e)
        {
            button.OnClick();
            bcounter.Increment();
        }

        // Root of execution - Obstantiates a LightSwitch object
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new LightSwitch());
        }
    }
}
