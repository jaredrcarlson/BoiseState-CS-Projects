using System.Drawing;
using System.Windows.Forms;

// The ToggleButton class represents a basic toggle button
// The toggle button object alternates between two "states"
public class ToggleButton : Button
{
    private string status; // Current State
    private string label1; // State 1
    private string label2; // State 2

    // Constructor has formal parameters for State 1 and State 2
    // Initializes the Current State to be State 1
    public ToggleButton(string label1, string label2)
	{
        this.label1 = label1;
        this.label2 = label2;
        status = this.label1;
        Font = new Font(Font, FontStyle.Bold);
        SetText();
	}

    // Toggles between states when the toggle button is clicked
    public void OnClick()
    {
        if(status.Equals(label1))
        {
            status = label2;
        }
        else
        {
            status = label1;
        }
        SetText();
    }

    // Sets the text for the toggle button to display current state
    private void SetText()
    {
        Text = status;
    }
}
