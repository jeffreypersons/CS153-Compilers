// Generated from simpL.g4 by ANTLR 4.7
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class simpLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, TYPE=6, LITERAL=7, NONE=8, TEXT=9, 
		NUMBER=10, BOOLEAN=11, TEXT_VALUE=12, NUMBER_VALUE=13, BOOLEAN_VALUE=14, 
		ASSIGN=15, SEPARATOR=16, LPAREN=17, RPAREN=18, LCURL=19, RCURL=20, LSQUARE=21, 
		RSQUARE=22, POW=23, MUL=24, DIV=25, ADD=26, SUB=27, LT=28, GT=29, LTE=30, 
		GTE=31, EQ=32, NEQ=33, NOT=34, AND=35, OR=36, EOL=37, WS=38, NAME=39, 
		LINE_COMMENT=40;
	public static final int
		RULE_program = 0, RULE_stmt = 1, RULE_declaration = 2, RULE_assignment = 3, 
		RULE_if_stmt = 4, RULE_func_def = 5, RULE_expr = 6, RULE_func_call = 7;
	public static final String[] ruleNames = {
		"program", "stmt", "declaration", "assignment", "if_stmt", "func_def", 
		"expr", "func_call"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'if'", "'else if'", "'else'", "'def'", "'return'", null, null, 
		"'None'", "'Text'", "'Number'", "'Boolean'", null, null, null, "'='", 
		"','", "'('", "')'", "'{'", "'}'", "'['", "']'", "'^'", "'*'", "'/'", 
		"'+'", "'-'", "'<'", "'>'", "'<='", "'>='", "'=='", "'!='", "'not'", "'and'", 
		"'or'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, "TYPE", "LITERAL", "NONE", "TEXT", 
		"NUMBER", "BOOLEAN", "TEXT_VALUE", "NUMBER_VALUE", "BOOLEAN_VALUE", "ASSIGN", 
		"SEPARATOR", "LPAREN", "RPAREN", "LCURL", "RCURL", "LSQUARE", "RSQUARE", 
		"POW", "MUL", "DIV", "ADD", "SUB", "LT", "GT", "LTE", "GTE", "EQ", "NEQ", 
		"NOT", "AND", "OR", "EOL", "WS", "NAME", "LINE_COMMENT"
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

	@Override
	public String getGrammarFileName() { return "simpL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public simpLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(19);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__3) | (1L << TYPE) | (1L << LITERAL) | (1L << LPAREN) | (1L << NOT) | (1L << NAME))) != 0)) {
				{
				{
				setState(16);
				stmt();
				}
				}
				setState(21);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmtContext extends ParserRuleContext {
		public Func_defContext func_def() {
			return getRuleContext(Func_defContext.class,0);
		}
		public If_stmtContext if_stmt() {
			return getRuleContext(If_stmtContext.class,0);
		}
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode EOL() { return getToken(simpLParser.EOL, 0); }
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).enterStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).exitStmt(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_stmt);
		try {
			setState(29);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(22);
				func_def();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(23);
				if_stmt();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(24);
				declaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(25);
				assignment();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(26);
				expr(0);
				setState(27);
				match(EOL);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public TerminalNode TYPE() { return getToken(simpLParser.TYPE, 0); }
		public TerminalNode NAME() { return getToken(simpLParser.NAME, 0); }
		public TerminalNode EOL() { return getToken(simpLParser.EOL, 0); }
		public TerminalNode ASSIGN() { return getToken(simpLParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).exitDeclaration(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(31);
			match(TYPE);
			setState(32);
			match(NAME);
			setState(35);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(33);
				match(ASSIGN);
				setState(34);
				expr(0);
				}
			}

			setState(37);
			match(EOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(simpLParser.NAME, 0); }
		public TerminalNode ASSIGN() { return getToken(simpLParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode EOL() { return getToken(simpLParser.EOL, 0); }
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).exitAssignment(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			match(NAME);
			setState(40);
			match(ASSIGN);
			setState(41);
			expr(0);
			setState(42);
			match(EOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class If_stmtContext extends ParserRuleContext {
		public List<TerminalNode> LPAREN() { return getTokens(simpLParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(simpLParser.LPAREN, i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> RPAREN() { return getTokens(simpLParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(simpLParser.RPAREN, i);
		}
		public List<TerminalNode> EOL() { return getTokens(simpLParser.EOL); }
		public TerminalNode EOL(int i) {
			return getToken(simpLParser.EOL, i);
		}
		public List<TerminalNode> LCURL() { return getTokens(simpLParser.LCURL); }
		public TerminalNode LCURL(int i) {
			return getToken(simpLParser.LCURL, i);
		}
		public List<TerminalNode> RCURL() { return getTokens(simpLParser.RCURL); }
		public TerminalNode RCURL(int i) {
			return getToken(simpLParser.RCURL, i);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public If_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).enterIf_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).exitIf_stmt(this);
		}
	}

	public final If_stmtContext if_stmt() throws RecognitionException {
		If_stmtContext _localctx = new If_stmtContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_if_stmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(44);
			match(T__0);
			setState(45);
			match(LPAREN);
			setState(46);
			expr(0);
			setState(47);
			match(RPAREN);
			setState(48);
			match(EOL);
			setState(49);
			match(LCURL);
			setState(50);
			match(EOL);
			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__3) | (1L << TYPE) | (1L << LITERAL) | (1L << LPAREN) | (1L << NOT) | (1L << NAME))) != 0)) {
				{
				{
				setState(51);
				stmt();
				}
				}
				setState(56);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(57);
			match(RCURL);
			setState(58);
			match(EOL);
			}
			setState(78);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(60);
				match(T__1);
				setState(61);
				match(LPAREN);
				setState(62);
				expr(0);
				setState(63);
				match(RPAREN);
				setState(64);
				match(EOL);
				setState(65);
				match(LCURL);
				setState(66);
				match(EOL);
				setState(70);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__3) | (1L << TYPE) | (1L << LITERAL) | (1L << LPAREN) | (1L << NOT) | (1L << NAME))) != 0)) {
					{
					{
					setState(67);
					stmt();
					}
					}
					setState(72);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(73);
				match(RCURL);
				setState(74);
				match(EOL);
				}
				}
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(81);
				match(T__2);
				setState(82);
				match(EOL);
				setState(83);
				match(LCURL);
				setState(84);
				match(EOL);
				setState(88);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__3) | (1L << TYPE) | (1L << LITERAL) | (1L << LPAREN) | (1L << NOT) | (1L << NAME))) != 0)) {
					{
					{
					setState(85);
					stmt();
					}
					}
					setState(90);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(91);
				match(RCURL);
				setState(92);
				match(EOL);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Func_defContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(simpLParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(simpLParser.NAME, i);
		}
		public TerminalNode LPAREN() { return getToken(simpLParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(simpLParser.RPAREN, 0); }
		public List<TerminalNode> EOL() { return getTokens(simpLParser.EOL); }
		public TerminalNode EOL(int i) {
			return getToken(simpLParser.EOL, i);
		}
		public TerminalNode LCURL() { return getToken(simpLParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(simpLParser.RCURL, 0); }
		public List<TerminalNode> TYPE() { return getTokens(simpLParser.TYPE); }
		public TerminalNode TYPE(int i) {
			return getToken(simpLParser.TYPE, i);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<TerminalNode> SEPARATOR() { return getTokens(simpLParser.SEPARATOR); }
		public TerminalNode SEPARATOR(int i) {
			return getToken(simpLParser.SEPARATOR, i);
		}
		public Func_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).enterFunc_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).exitFunc_def(this);
		}
	}

	public final Func_defContext func_def() throws RecognitionException {
		Func_defContext _localctx = new Func_defContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_func_def);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(T__3);
			setState(96);
			match(NAME);
			setState(97);
			match(LPAREN);
			setState(108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE) {
				{
				setState(98);
				match(TYPE);
				setState(99);
				match(NAME);
				setState(105);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SEPARATOR) {
					{
					{
					setState(100);
					match(SEPARATOR);
					setState(101);
					match(TYPE);
					setState(102);
					match(NAME);
					}
					}
					setState(107);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(110);
			match(RPAREN);
			setState(111);
			match(EOL);
			setState(112);
			match(LCURL);
			setState(113);
			match(EOL);
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__3) | (1L << TYPE) | (1L << LITERAL) | (1L << LPAREN) | (1L << NOT) | (1L << NAME))) != 0)) {
				{
				{
				setState(114);
				stmt();
				}
				}
				setState(119);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(124);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(120);
				match(T__4);
				setState(121);
				expr(0);
				setState(122);
				match(EOL);
				}
			}

			setState(126);
			match(RCURL);
			setState(127);
			match(EOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(simpLParser.NAME, 0); }
		public TerminalNode LITERAL() { return getToken(simpLParser.LITERAL, 0); }
		public Func_callContext func_call() {
			return getRuleContext(Func_callContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(simpLParser.LPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(simpLParser.RPAREN, 0); }
		public TerminalNode NOT() { return getToken(simpLParser.NOT, 0); }
		public TerminalNode POW() { return getToken(simpLParser.POW, 0); }
		public TerminalNode MUL() { return getToken(simpLParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(simpLParser.DIV, 0); }
		public TerminalNode ADD() { return getToken(simpLParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(simpLParser.SUB, 0); }
		public TerminalNode LT() { return getToken(simpLParser.LT, 0); }
		public TerminalNode GT() { return getToken(simpLParser.GT, 0); }
		public TerminalNode LTE() { return getToken(simpLParser.LTE, 0); }
		public TerminalNode GTE() { return getToken(simpLParser.GTE, 0); }
		public TerminalNode EQ() { return getToken(simpLParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(simpLParser.NEQ, 0); }
		public TerminalNode AND() { return getToken(simpLParser.AND, 0); }
		public TerminalNode OR() { return getToken(simpLParser.OR, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(130);
				match(NAME);
				}
				break;
			case 2:
				{
				setState(131);
				match(LITERAL);
				}
				break;
			case 3:
				{
				setState(132);
				func_call();
				}
				break;
			case 4:
				{
				setState(133);
				match(LPAREN);
				setState(134);
				expr(0);
				setState(135);
				match(RPAREN);
				}
				break;
			case 5:
				{
				setState(137);
				match(NOT);
				setState(138);
				expr(3);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(164);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(162);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(141);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(142);
						match(POW);
						setState(143);
						expr(9);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(144);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(145);
						_la = _input.LA(1);
						if ( !(_la==MUL || _la==DIV) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(146);
						expr(8);
						}
						break;
					case 3:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(147);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(148);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(149);
						expr(7);
						}
						break;
					case 4:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(150);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(151);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LT) | (1L << GT) | (1L << LTE) | (1L << GTE))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(152);
						expr(6);
						}
						break;
					case 5:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(153);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(154);
						_la = _input.LA(1);
						if ( !(_la==EQ || _la==NEQ) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(155);
						expr(5);
						}
						break;
					case 6:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(156);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(157);
						match(AND);
						setState(158);
						expr(3);
						}
						break;
					case 7:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(159);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(160);
						match(OR);
						setState(161);
						expr(2);
						}
						break;
					}
					} 
				}
				setState(166);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Func_callContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(simpLParser.NAME, 0); }
		public TerminalNode LPAREN() { return getToken(simpLParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(simpLParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> SEPARATOR() { return getTokens(simpLParser.SEPARATOR); }
		public TerminalNode SEPARATOR(int i) {
			return getToken(simpLParser.SEPARATOR, i);
		}
		public Func_callContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_call; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).enterFunc_call(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).exitFunc_call(this);
		}
	}

	public final Func_callContext func_call() throws RecognitionException {
		Func_callContext _localctx = new Func_callContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_func_call);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(167);
			match(NAME);
			setState(168);
			match(LPAREN);
			setState(177);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LITERAL) | (1L << LPAREN) | (1L << NOT) | (1L << NAME))) != 0)) {
				{
				setState(169);
				expr(0);
				setState(174);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SEPARATOR) {
					{
					{
					setState(170);
					match(SEPARATOR);
					setState(171);
					expr(0);
					}
					}
					setState(176);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(179);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 6:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 8);
		case 1:
			return precpred(_ctx, 7);
		case 2:
			return precpred(_ctx, 6);
		case 3:
			return precpred(_ctx, 5);
		case 4:
			return precpred(_ctx, 4);
		case 5:
			return precpred(_ctx, 2);
		case 6:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3*\u00b8\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\7\2\24\n\2"+
		"\f\2\16\2\27\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3 \n\3\3\4\3\4\3\4\3\4"+
		"\5\4&\n\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\7\6\67\n\6\f\6\16\6:\13\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\7\6G\n\6\f\6\16\6J\13\6\3\6\3\6\3\6\7\6O\n\6\f\6\16\6R\13\6\3\6\3\6\3"+
		"\6\3\6\3\6\7\6Y\n\6\f\6\16\6\\\13\6\3\6\3\6\5\6`\n\6\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\7\7j\n\7\f\7\16\7m\13\7\5\7o\n\7\3\7\3\7\3\7\3\7\3\7\7"+
		"\7v\n\7\f\7\16\7y\13\7\3\7\3\7\3\7\3\7\5\7\177\n\7\3\7\3\7\3\7\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u008e\n\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u00a5"+
		"\n\b\f\b\16\b\u00a8\13\b\3\t\3\t\3\t\3\t\3\t\7\t\u00af\n\t\f\t\16\t\u00b2"+
		"\13\t\5\t\u00b4\n\t\3\t\3\t\3\t\2\3\16\n\2\4\6\b\n\f\16\20\2\6\3\2\32"+
		"\33\3\2\34\35\3\2\36!\3\2\"#\2\u00cb\2\25\3\2\2\2\4\37\3\2\2\2\6!\3\2"+
		"\2\2\b)\3\2\2\2\n.\3\2\2\2\fa\3\2\2\2\16\u008d\3\2\2\2\20\u00a9\3\2\2"+
		"\2\22\24\5\4\3\2\23\22\3\2\2\2\24\27\3\2\2\2\25\23\3\2\2\2\25\26\3\2\2"+
		"\2\26\3\3\2\2\2\27\25\3\2\2\2\30 \5\f\7\2\31 \5\n\6\2\32 \5\6\4\2\33 "+
		"\5\b\5\2\34\35\5\16\b\2\35\36\7\'\2\2\36 \3\2\2\2\37\30\3\2\2\2\37\31"+
		"\3\2\2\2\37\32\3\2\2\2\37\33\3\2\2\2\37\34\3\2\2\2 \5\3\2\2\2!\"\7\b\2"+
		"\2\"%\7)\2\2#$\7\21\2\2$&\5\16\b\2%#\3\2\2\2%&\3\2\2\2&\'\3\2\2\2\'(\7"+
		"\'\2\2(\7\3\2\2\2)*\7)\2\2*+\7\21\2\2+,\5\16\b\2,-\7\'\2\2-\t\3\2\2\2"+
		"./\7\3\2\2/\60\7\23\2\2\60\61\5\16\b\2\61\62\7\24\2\2\62\63\7\'\2\2\63"+
		"\64\7\25\2\2\648\7\'\2\2\65\67\5\4\3\2\66\65\3\2\2\2\67:\3\2\2\28\66\3"+
		"\2\2\289\3\2\2\29;\3\2\2\2:8\3\2\2\2;<\7\26\2\2<=\7\'\2\2=P\3\2\2\2>?"+
		"\7\4\2\2?@\7\23\2\2@A\5\16\b\2AB\7\24\2\2BC\7\'\2\2CD\7\25\2\2DH\7\'\2"+
		"\2EG\5\4\3\2FE\3\2\2\2GJ\3\2\2\2HF\3\2\2\2HI\3\2\2\2IK\3\2\2\2JH\3\2\2"+
		"\2KL\7\26\2\2LM\7\'\2\2MO\3\2\2\2N>\3\2\2\2OR\3\2\2\2PN\3\2\2\2PQ\3\2"+
		"\2\2Q_\3\2\2\2RP\3\2\2\2ST\7\5\2\2TU\7\'\2\2UV\7\25\2\2VZ\7\'\2\2WY\5"+
		"\4\3\2XW\3\2\2\2Y\\\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[]\3\2\2\2\\Z\3\2\2\2]"+
		"^\7\26\2\2^`\7\'\2\2_S\3\2\2\2_`\3\2\2\2`\13\3\2\2\2ab\7\6\2\2bc\7)\2"+
		"\2cn\7\23\2\2de\7\b\2\2ek\7)\2\2fg\7\22\2\2gh\7\b\2\2hj\7)\2\2if\3\2\2"+
		"\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2\2lo\3\2\2\2mk\3\2\2\2nd\3\2\2\2no\3\2\2"+
		"\2op\3\2\2\2pq\7\24\2\2qr\7\'\2\2rs\7\25\2\2sw\7\'\2\2tv\5\4\3\2ut\3\2"+
		"\2\2vy\3\2\2\2wu\3\2\2\2wx\3\2\2\2x~\3\2\2\2yw\3\2\2\2z{\7\7\2\2{|\5\16"+
		"\b\2|}\7\'\2\2}\177\3\2\2\2~z\3\2\2\2~\177\3\2\2\2\177\u0080\3\2\2\2\u0080"+
		"\u0081\7\26\2\2\u0081\u0082\7\'\2\2\u0082\r\3\2\2\2\u0083\u0084\b\b\1"+
		"\2\u0084\u008e\7)\2\2\u0085\u008e\7\t\2\2\u0086\u008e\5\20\t\2\u0087\u0088"+
		"\7\23\2\2\u0088\u0089\5\16\b\2\u0089\u008a\7\24\2\2\u008a\u008e\3\2\2"+
		"\2\u008b\u008c\7$\2\2\u008c\u008e\5\16\b\5\u008d\u0083\3\2\2\2\u008d\u0085"+
		"\3\2\2\2\u008d\u0086\3\2\2\2\u008d\u0087\3\2\2\2\u008d\u008b\3\2\2\2\u008e"+
		"\u00a6\3\2\2\2\u008f\u0090\f\n\2\2\u0090\u0091\7\31\2\2\u0091\u00a5\5"+
		"\16\b\13\u0092\u0093\f\t\2\2\u0093\u0094\t\2\2\2\u0094\u00a5\5\16\b\n"+
		"\u0095\u0096\f\b\2\2\u0096\u0097\t\3\2\2\u0097\u00a5\5\16\b\t\u0098\u0099"+
		"\f\7\2\2\u0099\u009a\t\4\2\2\u009a\u00a5\5\16\b\b\u009b\u009c\f\6\2\2"+
		"\u009c\u009d\t\5\2\2\u009d\u00a5\5\16\b\7\u009e\u009f\f\4\2\2\u009f\u00a0"+
		"\7%\2\2\u00a0\u00a5\5\16\b\5\u00a1\u00a2\f\3\2\2\u00a2\u00a3\7&\2\2\u00a3"+
		"\u00a5\5\16\b\4\u00a4\u008f\3\2\2\2\u00a4\u0092\3\2\2\2\u00a4\u0095\3"+
		"\2\2\2\u00a4\u0098\3\2\2\2\u00a4\u009b\3\2\2\2\u00a4\u009e\3\2\2\2\u00a4"+
		"\u00a1\3\2\2\2\u00a5\u00a8\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a7\3\2"+
		"\2\2\u00a7\17\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a9\u00aa\7)\2\2\u00aa\u00b3"+
		"\7\23\2\2\u00ab\u00b0\5\16\b\2\u00ac\u00ad\7\22\2\2\u00ad\u00af\5\16\b"+
		"\2\u00ae\u00ac\3\2\2\2\u00af\u00b2\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b0\u00b1"+
		"\3\2\2\2\u00b1\u00b4\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b3\u00ab\3\2\2\2\u00b3"+
		"\u00b4\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b6\7\24\2\2\u00b6\21\3\2\2"+
		"\2\23\25\37%8HPZ_knw~\u008d\u00a4\u00a6\u00b0\u00b3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}