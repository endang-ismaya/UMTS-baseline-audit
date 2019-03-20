/**
 * 
 */
// package fontcolor;

/**
 * @author Aldeqeela
 * Apr 27, 2015 - 12:48:42 AM
 * Endang.Ismaya(endang.ismaya@gmail.com)
 * Walnut Creek, CA, USA
 * Font Color Constructor
 */
public class FontColor {

	// REGULAR
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\u001B[30m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_PURPLE = "\u001B[35m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final String ANSI_WHITE = "\u001B[37m";
	// BOLD COLOR
	private static final String ANSI_BBLACK = "\u001B[30;1m";
	private static final String ANSI_BRED = "\u001B[31;1m";
	private static final String ANSI_BGREEN = "\u001B[32;1m";
	private static final String ANSI_BYELLOW = "\u001B[33;1m";
	private static final String ANSI_BBLUE = "\u001B[34;1m";
	private static final String ANSI_BPURPLE = "\u001B[35;1m";
	private static final String ANSI_BCYAN = "\u001B[36;1m";
	private static final String ANSI_BWHITE = "\u001B[37;1m";
	// BG COLOR
	private static final String ANSI_BG_BLACK = "\u001B[40m";
	private static final String ANSI_BG_RED = "\u001B[41m";
	private static final String ANSI_BG_GREEN = "\u001B[42m";
	private static final String ANSI_BG_YELLOW = "\u001B[43m";
	private static final String ANSI_BG_BLUE = "\u001B[44m";
	private static final String ANSI_BG_PURPLE = "\u001B[45m";
	private static final String ANSI_BG_CYAN = "\u001B[46m";
	private static final String ANSI_BG_WHITE = "\u001B[47m";
	
	private static final String UNDERLINED = "\u001b[4m";
	
	String s;
	
	// 1st constructor
	public FontColor(String s){
		this.s = s;
	}
	
	// 2nd constructor
	public FontColor(){
		this.s = "";
	}
	
	// get method
	public String getString(){
		return s;
	}
	
	// set method
	public void setString(String s){
		this.s = s;
	}
	
	// REGULAR COLOR
	public String black(){
		return ANSI_BLACK + s + ANSI_RESET;
	}
	
	public String red(){
		return ANSI_RED + s + ANSI_RESET;
	}
	
	public String green(){
		return ANSI_GREEN + s + ANSI_RESET;
	}
	
	public String yellow(){
		return ANSI_YELLOW + s + ANSI_RESET;
	}
	
	public String blue(){
		return ANSI_BLUE + s + ANSI_RESET;
	}
	
	public String purple(){
		return ANSI_PURPLE + s + ANSI_RESET;
	}
	
	public String cyan(){
		return ANSI_CYAN + s + ANSI_RESET;
	}
	
	public String white(){
		return ANSI_WHITE + s + ANSI_RESET;
	}
	
	// BOLD COLOR
	public String bblack(){
		return ANSI_BBLACK + s + ANSI_RESET;
	}
	
	public String bred(){
		return ANSI_BRED + s + ANSI_RESET;
	}
	
	public String bgreen(){
		return ANSI_BGREEN + s + ANSI_RESET;
	}
	
	public String byellow(){
		return ANSI_BYELLOW + s + ANSI_RESET;
	}
	
	public String bblue(){
		return ANSI_BBLUE + s + ANSI_RESET;
	}
	
	public String bpurple(){
		return ANSI_BPURPLE + s + ANSI_RESET;
	}
	
	public String bcyan(){
		return ANSI_BCYAN + s + ANSI_RESET;
	}
	
	public String bwhite(){
		return ANSI_BWHITE + s + ANSI_RESET;
	}
	
	// BACKGROUND COLOR
		public String bgblack(){
			return ANSI_BG_BLACK + s + ANSI_RESET;
		}
		
		public String bgred(){
			return ANSI_BG_RED + s + ANSI_RESET;
		}
		
		public String bggreen(){
			return ANSI_BG_GREEN + s + ANSI_RESET;
		}
		
		public String bgyellow(){
			return ANSI_BG_YELLOW + s + ANSI_RESET;
		}
		
		public String bgblue(){
			return ANSI_BG_BLUE + s + ANSI_RESET;
		}
		
		public String bgpurple(){
			return ANSI_BG_PURPLE + s + ANSI_RESET;
		}
		
		public String bgcyan(){
			return ANSI_BG_CYAN + s + ANSI_RESET;
		}
		
		public String bgwhite(){
			return ANSI_BG_WHITE + s + ANSI_RESET;
		}
		
	// Underlined
		public String u(){
			return UNDERLINED + s + ANSI_RESET;
		}
	
	// background with font
		public String bggreen_bblack(){
			return ANSI_BG_GREEN + ANSI_BBLACK + s + ANSI_RESET;
		}
		
		public String bggreen_byellow(){
			return ANSI_BG_GREEN + ANSI_BYELLOW + s + ANSI_RESET;
		}
		
		public String bgblack_bblack(){
			return ANSI_BG_BLACK + ANSI_BBLACK + s + ANSI_RESET;
		}
		
		public String bgred_bwhite(){
			return ANSI_BG_RED + ANSI_BWHITE + s + ANSI_RESET;
		}
		
		public String bgblack_bwhite(){
			return ANSI_BG_BLACK + ANSI_BWHITE + s + ANSI_RESET;
		}
		
		public String bgyellow_bblack(){
			return ANSI_BG_YELLOW + ANSI_BBLACK + s + ANSI_RESET;
		}
		
		public String bgyellow_bwhite(){
			return ANSI_BG_YELLOW + ANSI_BWHITE + s + ANSI_RESET;
		}
}
