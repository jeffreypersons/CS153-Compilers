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
		"QUOTE", "DIGIT", "LETTER", "NEWLINE"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'if'", "'else if'", "'else'", "'def'", "'return'", null, null, 
		"'None'", "'Text'", "'Number'", "'Boolean'", null, null, null, "','", 
		"'('", "')'", "'{'", "'}'", "'['", "']'", "'^'", "'*'", "'/'", "'+'", 
		"'-'", "'<'", "'>'", "'<='", "'>='", "'=='", "'!='", "'not'", "'and'", 
		"'or'", "'='"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2+\u0135\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3"+
		"\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\5\7"+
		"|\n\7\3\b\3\b\3\b\5\b\u0081\n\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3"+
		"\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\r\3\r\7\r\u009e\n\r\f\r\16\r\u00a1\13\r\3\r\3\r\3\16\6\16\u00a6\n\16"+
		"\r\16\16\16\u00a7\3\16\6\16\u00ab\n\16\r\16\16\16\u00ac\3\16\3\16\6\16"+
		"\u00b1\n\16\r\16\16\16\u00b2\5\16\u00b5\n\16\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\5\17\u00c0\n\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23"+
		"\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32"+
		"\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3\37\3 \3"+
		" \3 \3!\3!\3!\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3$\3$\3$\3%\3%\3&\6&\u00f8\n"+
		"&\r&\16&\u00f9\3\'\3\'\5\'\u00fe\n\'\3\'\3\'\3\'\7\'\u0103\n\'\f\'\16"+
		"\'\u0106\13\'\3(\3(\6(\u010a\n(\r(\16(\u010b\5(\u010e\n(\3(\3(\3)\3)\7"+
		")\u0114\n)\f)\16)\u0117\13)\3)\3)\3)\3)\3*\3*\3*\3*\7*\u0121\n*\f*\16"+
		"*\u0124\13*\3*\3*\3*\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3.\5.\u0134\n.\5\u009f"+
		"\u0115\u0122\2/\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65"+
		"\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U\2W\2Y\2[\2\3\2\4\4\2\13\13"+
		"\"\"\4\2C\\c|\2\u0144\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2"+
		"\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2"+
		"\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3"+
		"\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2"+
		"\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2"+
		"Q\3\2\2\2\2S\3\2\2\2\3]\3\2\2\2\5`\3\2\2\2\7h\3\2\2\2\tm\3\2\2\2\13q\3"+
		"\2\2\2\r{\3\2\2\2\17\u0080\3\2\2\2\21\u0082\3\2\2\2\23\u0087\3\2\2\2\25"+
		"\u008c\3\2\2\2\27\u0093\3\2\2\2\31\u009b\3\2\2\2\33\u00b4\3\2\2\2\35\u00bf"+
		"\3\2\2\2\37\u00c1\3\2\2\2!\u00c3\3\2\2\2#\u00c5\3\2\2\2%\u00c7\3\2\2\2"+
		"\'\u00c9\3\2\2\2)\u00cb\3\2\2\2+\u00cd\3\2\2\2-\u00cf\3\2\2\2/\u00d1\3"+
		"\2\2\2\61\u00d3\3\2\2\2\63\u00d5\3\2\2\2\65\u00d7\3\2\2\2\67\u00d9\3\2"+
		"\2\29\u00db\3\2\2\2;\u00dd\3\2\2\2=\u00e0\3\2\2\2?\u00e3\3\2\2\2A\u00e6"+
		"\3\2\2\2C\u00e9\3\2\2\2E\u00ed\3\2\2\2G\u00f1\3\2\2\2I\u00f4\3\2\2\2K"+
		"\u00f7\3\2\2\2M\u00fd\3\2\2\2O\u010d\3\2\2\2Q\u0111\3\2\2\2S\u011c\3\2"+
		"\2\2U\u012a\3\2\2\2W\u012c\3\2\2\2Y\u012e\3\2\2\2[\u0133\3\2\2\2]^\7k"+
		"\2\2^_\7h\2\2_\4\3\2\2\2`a\7g\2\2ab\7n\2\2bc\7u\2\2cd\7g\2\2de\7\"\2\2"+
		"ef\7k\2\2fg\7h\2\2g\6\3\2\2\2hi\7g\2\2ij\7n\2\2jk\7u\2\2kl\7g\2\2l\b\3"+
		"\2\2\2mn\7f\2\2no\7g\2\2op\7h\2\2p\n\3\2\2\2qr\7t\2\2rs\7g\2\2st\7v\2"+
		"\2tu\7w\2\2uv\7t\2\2vw\7p\2\2w\f\3\2\2\2x|\5\23\n\2y|\5\25\13\2z|\5\27"+
		"\f\2{x\3\2\2\2{y\3\2\2\2{z\3\2\2\2|\16\3\2\2\2}\u0081\5\31\r\2~\u0081"+
		"\5\33\16\2\177\u0081\5\35\17\2\u0080}\3\2\2\2\u0080~\3\2\2\2\u0080\177"+
		"\3\2\2\2\u0081\20\3\2\2\2\u0082\u0083\7P\2\2\u0083\u0084\7q\2\2\u0084"+
		"\u0085\7p\2\2\u0085\u0086\7g\2\2\u0086\22\3\2\2\2\u0087\u0088\7V\2\2\u0088"+
		"\u0089\7g\2\2\u0089\u008a\7z\2\2\u008a\u008b\7v\2\2\u008b\24\3\2\2\2\u008c"+
		"\u008d\7P\2\2\u008d\u008e\7w\2\2\u008e\u008f\7o\2\2\u008f\u0090\7d\2\2"+
		"\u0090\u0091\7g\2\2\u0091\u0092\7t\2\2\u0092\26\3\2\2\2\u0093\u0094\7"+
		"D\2\2\u0094\u0095\7q\2\2\u0095\u0096\7q\2\2\u0096\u0097\7n\2\2\u0097\u0098"+
		"\7g\2\2\u0098\u0099\7c\2\2\u0099\u009a\7p\2\2\u009a\30\3\2\2\2\u009b\u009f"+
		"\5U+\2\u009c\u009e\13\2\2\2\u009d\u009c\3\2\2\2\u009e\u00a1\3\2\2\2\u009f"+
		"\u00a0\3\2\2\2\u009f\u009d\3\2\2\2\u00a0\u00a2\3\2\2\2\u00a1\u009f\3\2"+
		"\2\2\u00a2\u00a3\5U+\2\u00a3\32\3\2\2\2\u00a4\u00a6\5W,\2\u00a5\u00a4"+
		"\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8"+
		"\u00b5\3\2\2\2\u00a9\u00ab\5W,\2\u00aa\u00a9\3\2\2\2\u00ab\u00ac\3\2\2"+
		"\2\u00ac\u00aa\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00b0"+
		"\13\2\2\2\u00af\u00b1\5W,\2\u00b0\u00af\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2"+
		"\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b5\3\2\2\2\u00b4\u00a5\3\2"+
		"\2\2\u00b4\u00aa\3\2\2\2\u00b5\34\3\2\2\2\u00b6\u00b7\7v\2\2\u00b7\u00b8"+
		"\7t\2\2\u00b8\u00b9\7w\2\2\u00b9\u00c0\7g\2\2\u00ba\u00bb\7h\2\2\u00bb"+
		"\u00bc\7c\2\2\u00bc\u00bd\7n\2\2\u00bd\u00be\7u\2\2\u00be\u00c0\7g\2\2"+
		"\u00bf\u00b6\3\2\2\2\u00bf\u00ba\3\2\2\2\u00c0\36\3\2\2\2\u00c1\u00c2"+
		"\7.\2\2\u00c2 \3\2\2\2\u00c3\u00c4\7*\2\2\u00c4\"\3\2\2\2\u00c5\u00c6"+
		"\7+\2\2\u00c6$\3\2\2\2\u00c7\u00c8\7}\2\2\u00c8&\3\2\2\2\u00c9\u00ca\7"+
		"\177\2\2\u00ca(\3\2\2\2\u00cb\u00cc\7]\2\2\u00cc*\3\2\2\2\u00cd\u00ce"+
		"\7_\2\2\u00ce,\3\2\2\2\u00cf\u00d0\7`\2\2\u00d0.\3\2\2\2\u00d1\u00d2\7"+
		",\2\2\u00d2\60\3\2\2\2\u00d3\u00d4\7\61\2\2\u00d4\62\3\2\2\2\u00d5\u00d6"+
		"\7-\2\2\u00d6\64\3\2\2\2\u00d7\u00d8\7/\2\2\u00d8\66\3\2\2\2\u00d9\u00da"+
		"\7>\2\2\u00da8\3\2\2\2\u00db\u00dc\7@\2\2\u00dc:\3\2\2\2\u00dd\u00de\7"+
		">\2\2\u00de\u00df\7?\2\2\u00df<\3\2\2\2\u00e0\u00e1\7@\2\2\u00e1\u00e2"+
		"\7?\2\2\u00e2>\3\2\2\2\u00e3\u00e4\7?\2\2\u00e4\u00e5\7?\2\2\u00e5@\3"+
		"\2\2\2\u00e6\u00e7\7#\2\2\u00e7\u00e8\7?\2\2\u00e8B\3\2\2\2\u00e9\u00ea"+
		"\7p\2\2\u00ea\u00eb\7q\2\2\u00eb\u00ec\7v\2\2\u00ecD\3\2\2\2\u00ed\u00ee"+
		"\7c\2\2\u00ee\u00ef\7p\2\2\u00ef\u00f0\7f\2\2\u00f0F\3\2\2\2\u00f1\u00f2"+
		"\7q\2\2\u00f2\u00f3\7t\2\2\u00f3H\3\2\2\2\u00f4\u00f5\7?\2\2\u00f5J\3"+
		"\2\2\2\u00f6\u00f8\5[.\2\u00f7\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9"+
		"\u00f7\3\2\2\2\u00f9\u00fa\3\2\2\2\u00faL\3\2\2\2\u00fb\u00fe\7a\2\2\u00fc"+
		"\u00fe\5Y-\2\u00fd\u00fb\3\2\2\2\u00fd\u00fc\3\2\2\2\u00fe\u0104\3\2\2"+
		"\2\u00ff\u0103\7a\2\2\u0100\u0103\5Y-\2\u0101\u0103\5W,\2\u0102\u00ff"+
		"\3\2\2\2\u0102\u0100\3\2\2\2\u0102\u0101\3\2\2\2\u0103\u0106\3\2\2\2\u0104"+
		"\u0102\3\2\2\2\u0104\u0105\3\2\2\2\u0105N\3\2\2\2\u0106\u0104\3\2\2\2"+
		"\u0107\u010e\5Q)\2\u0108\u010a\t\2\2\2\u0109\u0108\3\2\2\2\u010a\u010b"+
		"\3\2\2\2\u010b\u0109\3\2\2\2\u010b\u010c\3\2\2\2\u010c\u010e\3\2\2\2\u010d"+
		"\u0107\3\2\2\2\u010d\u0109\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0110\b("+
		"\2\2\u0110P\3\2\2\2\u0111\u0115\7%\2\2\u0112\u0114\13\2\2\2\u0113\u0112"+
		"\3\2\2\2\u0114\u0117\3\2\2\2\u0115\u0116\3\2\2\2\u0115\u0113\3\2\2\2\u0116"+
		"\u0118\3\2\2\2\u0117\u0115\3\2\2\2\u0118\u0119\5[.\2\u0119\u011a\3\2\2"+
		"\2\u011a\u011b\b)\2\2\u011bR\3\2\2\2\u011c\u011d\7%\2\2\u011d\u011e\7"+
		"%\2\2\u011e\u0122\3\2\2\2\u011f\u0121\13\2\2\2\u0120\u011f\3\2\2\2\u0121"+
		"\u0124\3\2\2\2\u0122\u0123\3\2\2\2\u0122\u0120\3\2\2\2\u0123\u0125\3\2"+
		"\2\2\u0124\u0122\3\2\2\2\u0125\u0126\7%\2\2\u0126\u0127\7%\2\2\u0127\u0128"+
		"\3\2\2\2\u0128\u0129\b*\2\2\u0129T\3\2\2\2\u012a\u012b\7)\2\2\u012bV\3"+
		"\2\2\2\u012c\u012d\4\62;\2\u012dX\3\2\2\2\u012e\u012f\t\3\2\2\u012fZ\3"+
		"\2\2\2\u0130\u0134\7\f\2\2\u0131\u0132\7\17\2\2\u0132\u0134\7\f\2\2\u0133"+
		"\u0130\3\2\2\2\u0133\u0131\3\2\2\2\u0134\\\3\2\2\2\24\2{\u0080\u009f\u00a7"+
		"\u00ac\u00b2\u00b4\u00bf\u00f9\u00fd\u0102\u0104\u010b\u010d\u0115\u0122"+
		"\u0133\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}