using System;
using System.Drawing;
using System.Windows.Forms;

namespace LA5
{
    // The BinaryCounter class represents a simple binary "UP" counter
    public class BinaryCounter : Label
    {
        private int count; // Current count

        // Constructor has formal parameter for inital count
        public BinaryCounter(int i)
        {
            count = i;
            Font = new Font(Font, FontStyle.Bold);
            SetText();
        }

        // Increments the count by 1
        public void Increment()
        {
            count++;
            SetText();
        }

        // Sets the text to display the current count in Binary
        private void SetText()
        {
            Text = Convert.ToString(count, 2);
        }
    }
}