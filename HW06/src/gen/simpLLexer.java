// Generated from simpL.g4 by ANTLR 4.7
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class simpLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, TYPE=6, LITERAL=7, NONE=8, TEXT=9, 
		NUMBER=10, BOOLEAN=11, TEXT_VALUE=12, NUMBER_VALUE=13, BOOLEAN_VALUE=14, 
		SEPARATOR=15, LPAREN=16, RPAREN=17, LCURL=18, RCURL=19, LSQUARE=20, RSQUARE=21, 
		POW=22, MUL=23, DIV=24, ADD=25, SUB=26, LT=27, GT=28, LTE=29, GTE=30, 
		EQ=31, NEQ=32, NOT=33, AND=34, OR=35, ASSIGN=36, EOL=37, NAME=38, WHITESPACE=39, 
		LINE_COMMENT=40, BLOCK_COMMENT=41;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "TYPE", "LITERAL", "NONE", "TEXT", 
		"NUMBER", "BOOLEAN", "TEXT_VALUE", "NUMBER_VALUE", "BOOLEAN_VALUE", "SEPARATOR", 
		"LPAREN", "RPAREN", "LCURL", "RCURL", "LSQUARE", "RSQUARE", "POW", "MUL", 
		"DIV", "ADD", "SUB", "LT", "GT", "LTE", "GTE", "EQ", "NEQ", "NOT", "AND", 
		"OR", "ASSIGN", "EOL", "NAME", "WHITESPACE", "LINE_COMMENT", "BLOCK_COMMENT", 
		"QUOTE", "DIGIT", "LETTER", "NEWLINE", "STRING_ESCAPE"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'if'", "'elif'", "'else'", "'def'", "'return'", null, null, "'None'", 
		"'Text'", "'Number'", "'Boolean'", null, null, null, "','", "'('", "')'", 
		"'{'", "'}'", "'['", "']'", "'^'", "'*'", "'/'", "'+'", "'-'", "'<'", 
		"'>'", "'<='", "'>='", "'=='", "'!='", "'not'", "'and'", "'or'", "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, "TYPE", "LITERAL", "NONE", "TEXT", 
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


	public simpLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "simpL.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2+\u0136\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4"+
		"\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\5\7{\n\7"+
		"\3\b\3\b\3\b\5\b\u0080\n\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r"+
		"\7\r\u009d\n\r\f\r\16\r\u00a0\13\r\3\r\3\r\3\16\6\16\u00a5\n\16\r\16\16"+
		"\16\u00a6\3\16\6\16\u00aa\n\16\r\16\16\16\u00ab\3\16\3\16\6\16\u00b0\n"+
		"\16\r\16\16\16\u00b1\5\16\u00b4\n\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\5\17\u00bf\n\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24"+
		"\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33"+
		"\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3\37\3 \3 \3 \3!\3"+
		"!\3!\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3$\3$\3$\3%\3%\3&\6&\u00f7\n&\r&\16&"+
		"\u00f8\3\'\3\'\5\'\u00fd\n\'\3\'\3\'\3\'\7\'\u0102\n\'\f\'\16\'\u0105"+
		"\13\'\3(\3(\6(\u0109\n(\r(\16(\u010a\5(\u010d\n(\3(\3(\3)\3)\7)\u0113"+
		"\n)\f)\16)\u0116\13)\3)\3)\3)\3)\3*\3*\3*\3*\7*\u0120\n*\f*\16*\u0123"+
		"\13*\3*\3*\3*\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3.\5.\u0133\n.\3/\3/\5\u009e"+
		"\u0114\u0121\2\60\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65"+
		"\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U\2W\2Y\2[\2]\2\3\2\5\4\2\13"+
		"\13\"\"\4\2C\\c|\4\2))^^\2\u0144\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2"+
		"\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2"+
		"\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2"+
		"\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2"+
		"\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2"+
		"\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2"+
		"\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O"+
		"\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\3_\3\2\2\2\5b\3\2\2\2\7g\3\2\2\2\tl\3\2"+
		"\2\2\13p\3\2\2\2\rz\3\2\2\2\17\177\3\2\2\2\21\u0081\3\2\2\2\23\u0086\3"+
		"\2\2\2\25\u008b\3\2\2\2\27\u0092\3\2\2\2\31\u009a\3\2\2\2\33\u00b3\3\2"+
		"\2\2\35\u00be\3\2\2\2\37\u00c0\3\2\2\2!\u00c2\3\2\2\2#\u00c4\3\2\2\2%"+
		"\u00c6\3\2\2\2\'\u00c8\3\2\2\2)\u00ca\3\2\2\2+\u00cc\3\2\2\2-\u00ce\3"+
		"\2\2\2/\u00d0\3\2\2\2\61\u00d2\3\2\2\2\63\u00d4\3\2\2\2\65\u00d6\3\2\2"+
		"\2\67\u00d8\3\2\2\29\u00da\3\2\2\2;\u00dc\3\2\2\2=\u00df\3\2\2\2?\u00e2"+
		"\3\2\2\2A\u00e5\3\2\2\2C\u00e8\3\2\2\2E\u00ec\3\2\2\2G\u00f0\3\2\2\2I"+
		"\u00f3\3\2\2\2K\u00f6\3\2\2\2M\u00fc\3\2\2\2O\u010c\3\2\2\2Q\u0110\3\2"+
		"\2\2S\u011b\3\2\2\2U\u0129\3\2\2\2W\u012b\3\2\2\2Y\u012d\3\2\2\2[\u0132"+
		"\3\2\2\2]\u0134\3\2\2\2_`\7k\2\2`a\7h\2\2a\4\3\2\2\2bc\7g\2\2cd\7n\2\2"+
		"de\7k\2\2ef\7h\2\2f\6\3\2\2\2gh\7g\2\2hi\7n\2\2ij\7u\2\2jk\7g\2\2k\b\3"+
		"\2\2\2lm\7f\2\2mn\7g\2\2no\7h\2\2o\n\3\2\2\2pq\7t\2\2qr\7g\2\2rs\7v\2"+
		"\2st\7w\2\2tu\7t\2\2uv\7p\2\2v\f\3\2\2\2w{\5\23\n\2x{\5\25\13\2y{\5\27"+
		"\f\2zw\3\2\2\2zx\3\2\2\2zy\3\2\2\2{\16\3\2\2\2|\u0080\5\31\r\2}\u0080"+
		"\5\33\16\2~\u0080\5\35\17\2\177|\3\2\2\2\177}\3\2\2\2\177~\3\2\2\2\u0080"+
		"\20\3\2\2\2\u0081\u0082\7P\2\2\u0082\u0083\7q\2\2\u0083\u0084\7p\2\2\u0084"+
		"\u0085\7g\2\2\u0085\22\3\2\2\2\u0086\u0087\7V\2\2\u0087\u0088\7g\2\2\u0088"+
		"\u0089\7z\2\2\u0089\u008a\7v\2\2\u008a\24\3\2\2\2\u008b\u008c\7P\2\2\u008c"+
		"\u008d\7w\2\2\u008d\u008e\7o\2\2\u008e\u008f\7d\2\2\u008f\u0090\7g\2\2"+
		"\u0090\u0091\7t\2\2\u0091\26\3\2\2\2\u0092\u0093\7D\2\2\u0093\u0094\7"+
		"q\2\2\u0094\u0095\7q\2\2\u0095\u0096\7n\2\2\u0096\u0097\7g\2\2\u0097\u0098"+
		"\7c\2\2\u0098\u0099\7p\2\2\u0099\30\3\2\2\2\u009a\u009e\5U+\2\u009b\u009d"+
		"\13\2\2\2\u009c\u009b\3\2\2\2\u009d\u00a0\3\2\2\2\u009e\u009f\3\2\2\2"+
		"\u009e\u009c\3\2\2\2\u009f\u00a1\3\2\2\2\u00a0\u009e\3\2\2\2\u00a1\u00a2"+
		"\5U+\2\u00a2\32\3\2\2\2\u00a3\u00a5\5W,\2\u00a4\u00a3\3\2\2\2\u00a5\u00a6"+
		"\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00b4\3\2\2\2\u00a8"+
		"\u00aa\5W,\2\u00a9\u00a8\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00a9\3\2\2"+
		"\2\u00ab\u00ac\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00af\13\2\2\2\u00ae"+
		"\u00b0\5W,\2\u00af\u00ae\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00af\3\2\2"+
		"\2\u00b1\u00b2\3\2\2\2\u00b2\u00b4\3\2\2\2\u00b3\u00a4\3\2\2\2\u00b3\u00a9"+
		"\3\2\2\2\u00b4\34\3\2\2\2\u00b5\u00b6\7v\2\2\u00b6\u00b7\7t\2\2\u00b7"+
		"\u00b8\7w\2\2\u00b8\u00bf\7g\2\2\u00b9\u00ba\7h\2\2\u00ba\u00bb\7c\2\2"+
		"\u00bb\u00bc\7n\2\2\u00bc\u00bd\7u\2\2\u00bd\u00bf\7g\2\2\u00be\u00b5"+
		"\3\2\2\2\u00be\u00b9\3\2\2\2\u00bf\36\3\2\2\2\u00c0\u00c1\7.\2\2\u00c1"+
		" \3\2\2\2\u00c2\u00c3\7*\2\2\u00c3\"\3\2\2\2\u00c4\u00c5\7+\2\2\u00c5"+
		"$\3\2\2\2\u00c6\u00c7\7}\2\2\u00c7&\3\2\2\2\u00c8\u00c9\7\177\2\2\u00c9"+
		"(\3\2\2\2\u00ca\u00cb\7]\2\2\u00cb*\3\2\2\2\u00cc\u00cd\7_\2\2\u00cd,"+
		"\3\2\2\2\u00ce\u00cf\7`\2\2\u00cf.\3\2\2\2\u00d0\u00d1\7,\2\2\u00d1\60"+
		"\3\2\2\2\u00d2\u00d3\7\61\2\2\u00d3\62\3\2\2\2\u00d4\u00d5\7-\2\2\u00d5"+
		"\64\3\2\2\2\u00d6\u00d7\7/\2\2\u00d7\66\3\2\2\2\u00d8\u00d9\7>\2\2\u00d9"+
		"8\3\2\2\2\u00da\u00db\7@\2\2\u00db:\3\2\2\2\u00dc\u00dd\7>\2\2\u00dd\u00de"+
		"\7?\2\2\u00de<\3\2\2\2\u00df\u00e0\7@\2\2\u00e0\u00e1\7?\2\2\u00e1>\3"+
		"\2\2\2\u00e2\u00e3\7?\2\2\u00e3\u00e4\7?\2\2\u00e4@\3\2\2\2\u00e5\u00e6"+
		"\7#\2\2\u00e6\u00e7\7?\2\2\u00e7B\3\2\2\2\u00e8\u00e9\7p\2\2\u00e9\u00ea"+
		"\7q\2\2\u00ea\u00eb\7v\2\2\u00ebD\3\2\2\2\u00ec\u00ed\7c\2\2\u00ed\u00ee"+
		"\7p\2\2\u00ee\u00ef\7f\2\2\u00efF\3\2\2\2\u00f0\u00f1\7q\2\2\u00f1\u00f2"+
		"\7t\2\2\u00f2H\3\2\2\2\u00f3\u00f4\7?\2\2\u00f4J\3\2\2\2\u00f5\u00f7\5"+
		"[.\2\u00f6\u00f5\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8"+
		"\u00f9\3\2\2\2\u00f9L\3\2\2\2\u00fa\u00fd\7a\2\2\u00fb\u00fd\5Y-\2\u00fc"+
		"\u00fa\3\2\2\2\u00fc\u00fb\3\2\2\2\u00fd\u0103\3\2\2\2\u00fe\u0102\7a"+
		"\2\2\u00ff\u0102\5Y-\2\u0100\u0102\5W,\2\u0101\u00fe\3\2\2\2\u0101\u00ff"+
		"\3\2\2\2\u0101\u0100\3\2\2\2\u0102\u0105\3\2\2\2\u0103\u0101\3\2\2\2\u0103"+
		"\u0104\3\2\2\2\u0104N\3\2\2\2\u0105\u0103\3\2\2\2\u0106\u010d\5Q)\2\u0107"+
		"\u0109\t\2\2\2\u0108\u0107\3\2\2\2\u0109\u010a\3\2\2\2\u010a\u0108\3\2"+
		"\2\2\u010a\u010b\3\2\2\2\u010b\u010d\3\2\2\2\u010c\u0106\3\2\2\2\u010c"+
		"\u0108\3\2\2\2\u010d\u010e\3\2\2\2\u010e\u010f\b(\2\2\u010fP\3\2\2\2\u0110"+
		"\u0114\7%\2\2\u0111\u0113\13\2\2\2\u0112\u0111\3\2\2\2\u0113\u0116\3\2"+
		"\2\2\u0114\u0115\3\2\2\2\u0114\u0112\3\2\2\2\u0115\u0117\3\2\2\2\u0116"+
		"\u0114\3\2\2\2\u0117\u0118\5[.\2\u0118\u0119\3\2\2\2\u0119\u011a\b)\2"+
		"\2\u011aR\3\2\2\2\u011b\u011c\7%\2\2\u011c\u011d\7%\2\2\u011d\u0121\3"+
		"\2\2\2\u011e\u0120\13\2\2\2\u011f\u011e\3\2\2\2\u0120\u0123\3\2\2\2\u0121"+
		"\u0122\3\2\2\2\u0121\u011f\3\2\2\2\u0122\u0124\3\2\2\2\u0123\u0121\3\2"+
		"\2\2\u0124\u0125\7%\2\2\u0125\u0126\7%\2\2\u0126\u0127\3\2\2\2\u0127\u0128"+
		"\b*\2\2\u0128T\3\2\2\2\u0129\u012a\7)\2\2\u012aV\3\2\2\2\u012b\u012c\4"+
		"\62;\2\u012cX\3\2\2\2\u012d\u012e\t\3\2\2\u012eZ\3\2\2\2\u012f\u0133\7"+
		"\f\2\2\u0130\u0131\7\17\2\2\u0131\u0133\7\f\2\2\u0132\u012f\3\2\2\2\u0132"+
		"\u0130\3\2\2\2\u0133\\\3\2\2\2\u0134\u0135\t\4\2\2\u0135^\3\2\2\2\24\2"+
		"z\177\u009e\u00a6\u00ab\u00b1\u00b3\u00be\u00f8\u00fc\u0101\u0103\u010a"+
		"\u010c\u0114\u0121\u0132\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}