package com.mowitnow.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Observer;

import com.google.common.base.Preconditions;
import com.mowitnow.model.Lawn;
import com.mowitnow.model.Mower;
import com.mowitnow.model.Orientation;
import com.mowitnow.model.instruction.ForwardInstruction;
import com.mowitnow.model.instruction.TurnLeftInstruction;
import com.mowitnow.model.instruction.TurnRightInstruction;

/**
 * Parser of text representation of a lawn and its mowers.
 * 
 * @author mblanc
 */
public class Parser {

	private static final String SEPARATOR = " ";
	
	/**
	 * Parse a string representation of a lawn and its mowers.
	 * @param string the string.
	 * @param observer (used by MowItNowMessageInbound to send the successive states of the mowers through a websocket).
	 * @return the lawn.
	 * @throws IOException
	 */
	public Lawn parse(String string, Observer observer) throws IOException {
		Lawn lawn = parseInput(new StringReader(string));
		for (Mower mower : lawn.getMowers()) {
			mower.addObserver(observer);
		}
		return lawn;
	}

	/**
	 * Parse a file representation of a lawn and its mowers.
	 * @param file the file.
	 * @return the lawn.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Lawn parseFile(File file) throws FileNotFoundException, IOException {
		return parseInput(new FileReader(file));
	}

	private Lawn parseInput(Reader reader) throws IOException {
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(reader);
			String lawnDimensions = bufferedReader.readLine();
			Lawn lawn = parseLawn(lawnDimensions);
			int mowerId = 1;
			String mowerPositionLine;
			while ((mowerPositionLine = bufferedReader.readLine()) != null) {
				Mower mower = parseMower(mowerId, lawn, mowerPositionLine);
				String mowerInstructions = bufferedReader.readLine();
				parseInstructions(mower, mowerInstructions);
				lawn.addMower(mower);
				mowerId++;
			}
			return lawn;
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private Lawn parseLawn(String line) {
		Preconditions.checkNotNull(line, "Missing lawn dimension");
		Preconditions.checkArgument(line.matches("^\\d+ \\d+$"), "Cannot read lawn dimension : %s", line);
		
		String[] lawnDimension = line.split(SEPARATOR);
		Lawn lawn = new Lawn(Integer.parseInt(lawnDimension[0]), Integer.parseInt(lawnDimension[1]));
		return lawn;
	}
	
	private Mower parseMower(int id, Lawn lawn, String mowerPosition) {
		Preconditions.checkNotNull(lawn, "Missing lawn");
		Preconditions.checkNotNull(mowerPosition, "Missing mower position");
		Preconditions.checkArgument(mowerPosition.matches("^\\d+ \\d+ [N|E|W|S]$"), "Cannot read mower position and orientation : %s", mowerPosition);
		
		String[] mowerPositionAndOrientation = mowerPosition.split(SEPARATOR);
		Mower mower = new Mower(id, lawn, Integer.parseInt(mowerPositionAndOrientation[0]), Integer.parseInt(mowerPositionAndOrientation[1]),
				Orientation.fromCode(mowerPositionAndOrientation[2]));
		return mower;
	}
	
	private void parseInstructions(Mower mower, String instructions) {
		Preconditions.checkNotNull(mower, "Missing mower");
		Preconditions.checkNotNull(instructions, "Missing mower instructions");
		for (char instruction : instructions.toCharArray()) {
			switch (instruction) {
			case 'G':
				mower.addInstruction(new TurnLeftInstruction(mower));
				break;
			case 'D':
				mower.addInstruction(new TurnRightInstruction(mower));
				break;
			case 'A':
				mower.addInstruction(new ForwardInstruction(mower));
				break;
			default:
				throw new IllegalArgumentException("Unknow instruction : " + instruction);
			}
		}
	}

}
