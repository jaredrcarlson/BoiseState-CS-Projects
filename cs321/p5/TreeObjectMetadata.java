// TreeObject Meta-Data
public class TreeObjectMetadata {
	private final int NB_DNA_SEQUENCE_LENGTH = 1;
	private final int NB_NUM_LEADING_ZEROS_STRIPPED = 1;
	private final int NB_MD = NB_DNA_SEQUENCE_LENGTH + NB_NUM_LEADING_ZEROS_STRIPPED;
	
	private byte sequenceLength;
	private byte numberOfLeadingZerosStripped;
	
	public TreeObjectMetadata(String dnaString) {
		initDnaSequenceLength(dnaString);
		initNumberOfLeadingZerosStripped(dnaString);
	}
	
	// Construct from Disk Read
	public TreeObjectMetadata(byte sequenceLength, byte numberOfLeadingZerosStripped) {
		this.sequenceLength = sequenceLength;
		this.numberOfLeadingZerosStripped = numberOfLeadingZerosStripped;
	}
	
	public int getSize() {
		return NB_MD;
	}
	
	public byte getSequenceLength() {
		return sequenceLength;
	}
	
	public byte getNumberOfLeadingZerosStripped() {
		return numberOfLeadingZerosStripped;
	}
	
	public void addLeadingZero() {
		numberOfLeadingZerosStripped++;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("SeqLength=" + sequenceLength + ", NumStripZeros=" + numberOfLeadingZerosStripped);
		return sb.toString();
	}
	
	private void initDnaSequenceLength(String dnaString) {
		sequenceLength = ((byte)dnaString.length());
	}
	
	private void initNumberOfLeadingZerosStripped(String dnaString) {
		while(dnaString.length() > 0 && (dnaString.charAt(0) == 'A' || dnaString.charAt(0) == 'a')) {
			addLeadingZero();
			addLeadingZero();
			if(dnaString.length() > 1) {
				dnaString = dnaString.substring(1);
			}
			else {
				break;
			}
		}
		if(dnaString.charAt(0) == 'C' || dnaString.charAt(0) == 'c') {
			addLeadingZero();
		}
	}
}
