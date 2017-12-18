// Generated from /mnt/c/Users/jperm/Dropbox/College/SJSU/CS153-Compilers/SimpL/src/SimpL.g4 by ANTLR 4.7
package gen;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SimpLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, TYPE=7, LITERAL=8, NONE=9, 
		TEXT=10, NUMBER=11, BOOLEAN=12, TEXT_VALUE=13, NUMBER_VALUE=14, BOOLEAN_VALUE=15, 
		SEPARATOR=16, LPAREN=17, RPAREN=18, LCURL=19, RCURL=20, LSQUARE=21, RSQUARE=22, 
		POW=23, MUL=24, DIV=25, ADD=26, SUB=27, LT=28, GT=29, LTE=30, GTE=31, 
		EQ=32, NEQ=33, NOT=34, AND=35, OR=36, ASSIGN=37, EOL=38, NAME=39, WHITESPACE=40, 
		LINE_COMMENT=41, BLOCK_COMMENT=42;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "TYPE", "LITERAL", "NONE", 
		"TEXT", "NUMBER", "BOOLEAN", "TEXT_VALUE", "NUMBER_VALUE", "BOOLEAN_VALUE", 
		"SEPARATOR", "LPAREN", "RPAREN", "LCURL", "RCURL", "LSQUARE", "RSQUARE", 
		"POW", "MUL", "DIV", "ADD", "SUB", "LT", "GT", "LTE", "GTE", "EQ", "NEQ", 
		"NOT", "AND", "OR", "ASSIGN", "EOL", "NAME", "WHITESPACE", "LINE_COMMENT", 
		"BLOCK_COMMENT", "QUOTE", "DIGIT", "LETTER", "NEWLINE", "BACKSLASH"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'while'", "'if'", "'elif'", "'else'", "'Void'", "'return'", null, 
		null, "'None'", "'Text'", "'Number'", "'Boolean'", null, null, null, "','", 
		"'('", "')'", "'{'", "'}'", "'['", "']'", "'^'", "'*'", "'/'", "'+'", 
		"'-'", "'<'", "'>'", "'<='", "'>='", "'=='", "'!='", "'not'", "'and'", 
		"'or'", "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, "TYPE", "LITERAL", "NONE", "TEXT", 
		"NUMBER", "BOOLEAN", "TEXT_VALUE", "NUMBER_VALUE", "BOOLEAN_VALUE", "SEPARATOR", 
		"LPAREN", "RPAREN", "LCURL", "RCURL", "LSQUARE", "RSQUARE", "POW", "MUL", 
		"DIV", "ADD", "SUB", "LT", "GT", "LTE", "GTE", "EQ", "NEQ", "NOT", "AND", 
		"OR", "ASSIGN", "EOL", "NAME", "WHITESPACE", "LINE_COMMENT", "BLOCK_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public SimpLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SimpL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2,\u0143\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3"+
		"\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\b\3\b\3\b\5\b\u0084\n\b\3\t\3\t\3\t\5\t\u0089\n\t\3"+
		"\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\7\16"+
		"\u00aa\n\16\f\16\16\16\u00ad\13\16\3\16\3\16\3\17\6\17\u00b2\n\17\r\17"+
		"\16\17\u00b3\3\17\6\17\u00b7\n\17\r\17\16\17\u00b8\3\17\3\17\6\17\u00bd"+
		"\n\17\r\17\16\17\u00be\5\17\u00c1\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\5\20\u00cc\n\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24"+
		"\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33"+
		"\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3"+
		"\"\3\"\3#\3#\3#\3#\3$\3$\3$\3$\3%\3%\3%\3&\3&\3\'\6\'\u0104\n\'\r\'\16"+
		"\'\u0105\3(\3(\5(\u010a\n(\3(\3(\3(\7(\u010f\n(\f(\16(\u0112\13(\3)\3"+
		")\6)\u0116\n)\r)\16)\u0117\5)\u011a\n)\3)\3)\3*\3*\7*\u0120\n*\f*\16*"+
		"\u0123\13*\3*\3*\3*\3*\3+\3+\3+\3+\7+\u012d\n+\f+\16+\u0130\13+\3+\3+"+
		"\3+\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3/\5/\u0140\n/\3\60\3\60\4\u0121\u012e"+
		"\2\61\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36"+
		";\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W\2Y\2[\2]\2_\2\3\2\5\5\2\f\f\17\17))"+
		"\4\2\13\13\"\"\4\2C\\c|\2\u0153\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2"+
		"\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2"+
		"\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2"+
		"\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2"+
		"\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2"+
		"\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2"+
		"\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O"+
		"\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\3a\3\2\2\2\5g\3\2\2\2\7j\3\2"+
		"\2\2\to\3\2\2\2\13t\3\2\2\2\ry\3\2\2\2\17\u0083\3\2\2\2\21\u0088\3\2\2"+
		"\2\23\u008a\3\2\2\2\25\u008f\3\2\2\2\27\u0094\3\2\2\2\31\u009b\3\2\2\2"+
		"\33\u00a3\3\2\2\2\35\u00c0\3\2\2\2\37\u00cb\3\2\2\2!\u00cd\3\2\2\2#\u00cf"+
		"\3\2\2\2%\u00d1\3\2\2\2\'\u00d3\3\2\2\2)\u00d5\3\2\2\2+\u00d7\3\2\2\2"+
		"-\u00d9\3\2\2\2/\u00db\3\2\2\2\61\u00dd\3\2\2\2\63\u00df\3\2\2\2\65\u00e1"+
		"\3\2\2\2\67\u00e3\3\2\2\29\u00e5\3\2\2\2;\u00e7\3\2\2\2=\u00e9\3\2\2\2"+
		"?\u00ec\3\2\2\2A\u00ef\3\2\2\2C\u00f2\3\2\2\2E\u00f5\3\2\2\2G\u00f9\3"+
		"\2\2\2I\u00fd\3\2\2\2K\u0100\3\2\2\2M\u0103\3\2\2\2O\u0109\3\2\2\2Q\u0119"+
		"\3\2\2\2S\u011d\3\2\2\2U\u0128\3\2\2\2W\u0136\3\2\2\2Y\u0138\3\2\2\2["+
		"\u013a\3\2\2\2]\u013f\3\2\2\2_\u0141\3\2\2\2ab\7y\2\2bc\7j\2\2cd\7k\2"+
		"\2de\7n\2\2ef\7g\2\2f\4\3\2\2\2gh\7k\2\2hi\7h\2\2i\6\3\2\2\2jk\7g\2\2"+
		"kl\7n\2\2lm\7k\2\2mn\7h\2\2n\b\3\2\2\2op\7g\2\2pq\7n\2\2qr\7u\2\2rs\7"+
		"g\2\2s\n\3\2\2\2tu\7X\2\2uv\7q\2\2vw\7k\2\2wx\7f\2\2x\f\3\2\2\2yz\7t\2"+
		"\2z{\7g\2\2{|\7v\2\2|}\7w\2\2}~\7t\2\2~\177\7p\2\2\177\16\3\2\2\2\u0080"+
		"\u0084\5\25\13\2\u0081\u0084\5\27\f\2\u0082\u0084\5\31\r\2\u0083\u0080"+
		"\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0082\3\2\2\2\u0084\20\3\2\2\2\u0085"+
		"\u0089\5\33\16\2\u0086\u0089\5\35\17\2\u0087\u0089\5\37\20\2\u0088\u0085"+
		"\3\2\2\2\u0088\u0086\3\2\2\2\u0088\u0087\3\2\2\2\u0089\22\3\2\2\2\u008a"+
		"\u008b\7P\2\2\u008b\u008c\7q\2\2\u008c\u008d\7p\2\2\u008d\u008e\7g\2\2"+
		"\u008e\24\3\2\2\2\u008f\u0090\7V\2\2\u0090\u0091\7g\2\2\u0091\u0092\7"+
		"z\2\2\u0092\u0093\7v\2\2\u0093\26\3\2\2\2\u0094\u0095\7P\2\2\u0095\u0096"+
		"\7w\2\2\u0096\u0097\7o\2\2\u0097\u0098\7d\2\2\u0098\u0099\7g\2\2\u0099"+
		"\u009a\7t\2\2\u009a\30\3\2\2\2\u009b\u009c\7D\2\2\u009c\u009d\7q\2\2\u009d"+
		"\u009e\7q\2\2\u009e\u009f\7n\2\2\u009f\u00a0\7g\2\2\u00a0\u00a1\7c\2\2"+
		"\u00a1\u00a2\7p\2\2\u00a2\32\3\2\2\2\u00a3\u00ab\5W,\2\u00a4\u00aa\n\2"+
		"\2\2\u00a5\u00a6\7^\2\2\u00a6\u00aa\7)\2\2\u00a7\u00a8\7^\2\2\u00a8\u00aa"+
		"\7^\2\2\u00a9\u00a4\3\2\2\2\u00a9\u00a5\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa"+
		"\u00ad\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00ae\3\2"+
		"\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00af\5W,\2\u00af\34\3\2\2\2\u00b0\u00b2"+
		"\5Y-\2\u00b1\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3"+
		"\u00b4\3\2\2\2\u00b4\u00c1\3\2\2\2\u00b5\u00b7\5Y-\2\u00b6\u00b5\3\2\2"+
		"\2\u00b7\u00b8\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00ba"+
		"\3\2\2\2\u00ba\u00bc\13\2\2\2\u00bb\u00bd\5Y-\2\u00bc\u00bb\3\2\2\2\u00bd"+
		"\u00be\3\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c1\3\2"+
		"\2\2\u00c0\u00b1\3\2\2\2\u00c0\u00b6\3\2\2\2\u00c1\36\3\2\2\2\u00c2\u00c3"+
		"\7v\2\2\u00c3\u00c4\7t\2\2\u00c4\u00c5\7w\2\2\u00c5\u00cc\7g\2\2\u00c6"+
		"\u00c7\7h\2\2\u00c7\u00c8\7c\2\2\u00c8\u00c9\7n\2\2\u00c9\u00ca\7u\2\2"+
		"\u00ca\u00cc\7g\2\2\u00cb\u00c2\3\2\2\2\u00cb\u00c6\3\2\2\2\u00cc \3\2"+
		"\2\2\u00cd\u00ce\7.\2\2\u00ce\"\3\2\2\2\u00cf\u00d0\7*\2\2\u00d0$\3\2"+
		"\2\2\u00d1\u00d2\7+\2\2\u00d2&\3\2\2\2\u00d3\u00d4\7}\2\2\u00d4(\3\2\2"+
		"\2\u00d5\u00d6\7\177\2\2\u00d6*\3\2\2\2\u00d7\u00d8\7]\2\2\u00d8,\3\2"+
		"\2\2\u00d9\u00da\7_\2\2\u00da.\3\2\2\2\u00db\u00dc\7`\2\2\u00dc\60\3\2"+
		"\2\2\u00dd\u00de\7,\2\2\u00de\62\3\2\2\2\u00df\u00e0\7\61\2\2\u00e0\64"+
		"\3\2\2\2\u00e1\u00e2\7-\2\2\u00e2\66\3\2\2\2\u00e3\u00e4\7/\2\2\u00e4"+
		"8\3\2\2\2\u00e5\u00e6\7>\2\2\u00e6:\3\2\2\2\u00e7\u00e8\7@\2\2\u00e8<"+
		"\3\2\2\2\u00e9\u00ea\7>\2\2\u00ea\u00eb\7?\2\2\u00eb>\3\2\2\2\u00ec\u00ed"+
		"\7@\2\2\u00ed\u00ee\7?\2\2\u00ee@\3\2\2\2\u00ef\u00f0\7?\2\2\u00f0\u00f1"+
		"\7?\2\2\u00f1B\3\2\2\2\u00f2\u00f3\7#\2\2\u00f3\u00f4\7?\2\2\u00f4D\3"+
		"\2\2\2\u00f5\u00f6\7p\2\2\u00f6\u00f7\7q\2\2\u00f7\u00f8\7v\2\2\u00f8"+
		"F\3\2\2\2\u00f9\u00fa\7c\2\2\u00fa\u00fb\7p\2\2\u00fb\u00fc\7f\2\2\u00fc"+
		"H\3\2\2\2\u00fd\u00fe\7q\2\2\u00fe\u00ff\7t\2\2\u00ffJ\3\2\2\2\u0100\u0101"+
		"\7?\2\2\u0101L\3\2\2\2\u0102\u0104\5]/\2\u0103\u0102\3\2\2\2\u0104\u0105"+
		"\3\2\2\2\u0105\u0103\3\2\2\2\u0105\u0106\3\2\2\2\u0106N\3\2\2\2\u0107"+
		"\u010a\7a\2\2\u0108\u010a\5[.\2\u0109\u0107\3\2\2\2\u0109\u0108\3\2\2"+
		"\2\u010a\u0110\3\2\2\2\u010b\u010f\7a\2\2\u010c\u010f\5[.\2\u010d\u010f"+
		"\5Y-\2\u010e\u010b\3\2\2\2\u010e\u010c\3\2\2\2\u010e\u010d\3\2\2\2\u010f"+
		"\u0112\3\2\2\2\u0110\u010e\3\2\2\2\u0110\u0111\3\2\2\2\u0111P\3\2\2\2"+
		"\u0112\u0110\3\2\2\2\u0113\u011a\5S*\2\u0114\u0116\t\3\2\2\u0115\u0114"+
		"\3\2\2\2\u0116\u0117\3\2\2\2\u0117\u0115\3\2\2\2\u0117\u0118\3\2\2\2\u0118"+
		"\u011a\3\2\2\2\u0119\u0113\3\2\2\2\u0119\u0115\3\2\2\2\u011a\u011b\3\2"+
		"\2\2\u011b\u011c\b)\2\2\u011cR\3\2\2\2\u011d\u0121\7%\2\2\u011e\u0120"+
		"\13\2\2\2\u011f\u011e\3\2\2\2\u0120\u0123\3\2\2\2\u0121\u0122\3\2\2\2"+
		"\u0121\u011f\3\2\2\2\u0122\u0124\3\2\2\2\u0123\u0121\3\2\2\2\u0124\u0125"+
		"\5]/\2\u0125\u0126\3\2\2\2\u0126\u0127\b*\2\2\u0127T\3\2\2\2\u0128\u0129"+
		"\7%\2\2\u0129\u012a\7%\2\2\u012a\u012e\3\2\2\2\u012b\u012d\13\2\2\2\u012c"+
		"\u012b\3\2\2\2\u012d\u0130\3\2\2\2\u012e\u012f\3\2\2\2\u012e\u012c\3\2"+
		"\2\2\u012f\u0131\3\2\2\2\u0130\u012e\3\2\2\2\u0131\u0132\7%\2\2\u0132"+
		"\u0133\7%\2\2\u0133\u0134\3\2\2\2\u0134\u0135\b+\2\2\u0135V\3\2\2\2\u0136"+
		"\u0137\7)\2\2\u0137X\3\2\2\2\u0138\u0139\4\62;\2\u0139Z\3\2\2\2\u013a"+
		"\u013b\t\4\2\2\u013b\\\3\2\2\2\u013c\u0140\7\f\2\2\u013d\u013e\7\17\2"+
		"\2\u013e\u0140\7\f\2\2\u013f\u013c\3\2\2\2\u013f\u013d\3\2\2\2\u0140^"+
		"\3\2\2\2\u0141\u0142\7^\2\2\u0142`\3\2\2\2\25\2\u0083\u0088\u00a9\u00ab"+
		"\u00b3\u00b8\u00be\u00c0\u00cb\u0105\u0109\u010e\u0110\u0117\u0119\u0121"+
		"\u012e\u013f\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}