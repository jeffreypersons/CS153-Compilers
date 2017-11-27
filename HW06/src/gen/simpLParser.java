// Generated from C:/Users/jperm/Dropbox/College/SJSU/CS153-Compilers/Assignments/HW06/src\simpL.g4 by ANTLR 4.7
package gen;
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
		SEPARATOR=15, LPAREN=16, RPAREN=17, LCURL=18, RCURL=19, LSQUARE=20, RSQUARE=21, 
		POW=22, MUL=23, DIV=24, ADD=25, SUB=26, LT=27, GT=28, LTE=29, GTE=30, 
		EQ=31, NEQ=32, NOT=33, AND=34, OR=35, ASSIGN=36, EOL=37, NAME=38, WHITESPACE=39, 
		LINE_COMMENT=40, BLOCK_COMMENT=41;
	public static final int
		RULE_program = 0, RULE_stmt = 1, RULE_declaration = 2, RULE_assignment = 3, 
		RULE_if_stmt = 4, RULE_func_def = 5, RULE_block = 6, RULE_expr = 7, RULE_func_call = 8;
	public static final String[] ruleNames = {
		"program", "stmt", "declaration", "assignment", "if_stmt", "func_def", 
		"block", "expr", "func_call"
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpLVisitor ) return ((simpLVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__3) | (1L << TYPE) | (1L << LITERAL) | (1L << LPAREN) | (1L << NOT) | (1L << NAME))) != 0)) {
				{
				{
				setState(18);
				stmt();
				}
				}
				setState(23);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpLVisitor ) return ((simpLVisitor<? extends T>)visitor).visitStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_stmt);
		try {
			setState(31);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(24);
				func_def();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(25);
				if_stmt();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(26);
				declaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(27);
				assignment();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(28);
				expr(0);
				setState(29);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpLVisitor ) return ((simpLVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			match(TYPE);
			setState(34);
			match(NAME);
			setState(37);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(35);
				match(ASSIGN);
				setState(36);
				expr(0);
				}
			}

			setState(39);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpLVisitor ) return ((simpLVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			match(NAME);
			setState(42);
			match(ASSIGN);
			setState(43);
			expr(0);
			setState(44);
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
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpLVisitor ) return ((simpLVisitor<? extends T>)visitor).visitIf_stmt(this);
			else return visitor.visitChildren(this);
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
			setState(46);
			match(T__0);
			setState(47);
			expr(0);
			setState(48);
			block();
			}
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(50);
				match(T__1);
				setState(51);
				expr(0);
				setState(52);
				block();
				}
				}
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(59);
				match(T__2);
				setState(60);
				block();
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
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TerminalNode> TYPE() { return getTokens(simpLParser.TYPE); }
		public TerminalNode TYPE(int i) {
			return getToken(simpLParser.TYPE, i);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpLVisitor ) return ((simpLVisitor<? extends T>)visitor).visitFunc_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Func_defContext func_def() throws RecognitionException {
		Func_defContext _localctx = new Func_defContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_func_def);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			match(T__3);
			setState(64);
			match(NAME);
			setState(65);
			match(LPAREN);
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE) {
				{
				setState(66);
				match(TYPE);
				setState(67);
				match(NAME);
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SEPARATOR) {
					{
					{
					setState(68);
					match(SEPARATOR);
					setState(69);
					match(TYPE);
					setState(70);
					match(NAME);
					}
					}
					setState(75);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(78);
			match(RPAREN);
			setState(79);
			block();
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

	public static class BlockContext extends ParserRuleContext {
		public List<TerminalNode> EOL() { return getTokens(simpLParser.EOL); }
		public TerminalNode EOL(int i) {
			return getToken(simpLParser.EOL, i);
		}
		public TerminalNode LCURL() { return getToken(simpLParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(simpLParser.RCURL, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpLListener ) ((simpLListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpLVisitor ) return ((simpLVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(EOL);
			setState(82);
			match(LCURL);
			setState(83);
			match(EOL);
			setState(87);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__3) | (1L << TYPE) | (1L << LITERAL) | (1L << LPAREN) | (1L << NOT) | (1L << NAME))) != 0)) {
				{
				{
				setState(84);
				stmt();
				}
				}
				setState(89);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(92);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(90);
				match(T__4);
				setState(91);
				expr(0);
				}
			}

			setState(94);
			match(EOL);
			setState(95);
			match(RCURL);
			setState(96);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpLVisitor ) return ((simpLVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
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
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(99);
				match(NAME);
				}
				break;
			case 2:
				{
				setState(100);
				match(LITERAL);
				}
				break;
			case 3:
				{
				setState(101);
				func_call();
				}
				break;
			case 4:
				{
				setState(102);
				match(LPAREN);
				setState(103);
				expr(0);
				setState(104);
				match(RPAREN);
				}
				break;
			case 5:
				{
				setState(106);
				match(NOT);
				setState(107);
				expr(3);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(133);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(131);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(110);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(111);
						match(POW);
						setState(112);
						expr(9);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(113);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(114);
						_la = _input.LA(1);
						if ( !(_la==MUL || _la==DIV) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(115);
						expr(8);
						}
						break;
					case 3:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(116);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(117);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(118);
						expr(7);
						}
						break;
					case 4:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(119);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(120);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LT) | (1L << GT) | (1L << LTE) | (1L << GTE))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(121);
						expr(6);
						}
						break;
					case 5:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(122);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(123);
						_la = _input.LA(1);
						if ( !(_la==EQ || _la==NEQ) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(124);
						expr(5);
						}
						break;
					case 6:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(125);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(126);
						match(AND);
						setState(127);
						expr(3);
						}
						break;
					case 7:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(128);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(129);
						match(OR);
						setState(130);
						expr(2);
						}
						break;
					}
					} 
				}
				setState(135);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpLVisitor ) return ((simpLVisitor<? extends T>)visitor).visitFunc_call(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Func_callContext func_call() throws RecognitionException {
		Func_callContext _localctx = new Func_callContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_func_call);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			match(NAME);
			setState(137);
			match(LPAREN);
			setState(146);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LITERAL) | (1L << LPAREN) | (1L << NOT) | (1L << NAME))) != 0)) {
				{
				setState(138);
				expr(0);
				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SEPARATOR) {
					{
					{
					setState(139);
					match(SEPARATOR);
					setState(140);
					expr(0);
					}
					}
					setState(145);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(148);
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
		case 7:
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3+\u0099\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\7\2"+
		"\26\n\2\f\2\16\2\31\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\"\n\3\3\4\3\4"+
		"\3\4\3\4\5\4(\n\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\7\69\n\6\f\6\16\6<\13\6\3\6\3\6\5\6@\n\6\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\7\7J\n\7\f\7\16\7M\13\7\5\7O\n\7\3\7\3\7\3\7\3\b\3\b\3\b"+
		"\3\b\7\bX\n\b\f\b\16\b[\13\b\3\b\3\b\5\b_\n\b\3\b\3\b\3\b\3\b\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\to\n\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u0086\n\t"+
		"\f\t\16\t\u0089\13\t\3\n\3\n\3\n\3\n\3\n\7\n\u0090\n\n\f\n\16\n\u0093"+
		"\13\n\5\n\u0095\n\n\3\n\3\n\3\n\2\3\20\13\2\4\6\b\n\f\16\20\22\2\6\3\2"+
		"\31\32\3\2\33\34\3\2\35 \3\2!\"\2\u00a8\2\27\3\2\2\2\4!\3\2\2\2\6#\3\2"+
		"\2\2\b+\3\2\2\2\n\60\3\2\2\2\fA\3\2\2\2\16S\3\2\2\2\20n\3\2\2\2\22\u008a"+
		"\3\2\2\2\24\26\5\4\3\2\25\24\3\2\2\2\26\31\3\2\2\2\27\25\3\2\2\2\27\30"+
		"\3\2\2\2\30\3\3\2\2\2\31\27\3\2\2\2\32\"\5\f\7\2\33\"\5\n\6\2\34\"\5\6"+
		"\4\2\35\"\5\b\5\2\36\37\5\20\t\2\37 \7\'\2\2 \"\3\2\2\2!\32\3\2\2\2!\33"+
		"\3\2\2\2!\34\3\2\2\2!\35\3\2\2\2!\36\3\2\2\2\"\5\3\2\2\2#$\7\b\2\2$\'"+
		"\7(\2\2%&\7&\2\2&(\5\20\t\2\'%\3\2\2\2\'(\3\2\2\2()\3\2\2\2)*\7\'\2\2"+
		"*\7\3\2\2\2+,\7(\2\2,-\7&\2\2-.\5\20\t\2./\7\'\2\2/\t\3\2\2\2\60\61\7"+
		"\3\2\2\61\62\5\20\t\2\62\63\5\16\b\2\63:\3\2\2\2\64\65\7\4\2\2\65\66\5"+
		"\20\t\2\66\67\5\16\b\2\679\3\2\2\28\64\3\2\2\29<\3\2\2\2:8\3\2\2\2:;\3"+
		"\2\2\2;?\3\2\2\2<:\3\2\2\2=>\7\5\2\2>@\5\16\b\2?=\3\2\2\2?@\3\2\2\2@\13"+
		"\3\2\2\2AB\7\6\2\2BC\7(\2\2CN\7\22\2\2DE\7\b\2\2EK\7(\2\2FG\7\21\2\2G"+
		"H\7\b\2\2HJ\7(\2\2IF\3\2\2\2JM\3\2\2\2KI\3\2\2\2KL\3\2\2\2LO\3\2\2\2M"+
		"K\3\2\2\2ND\3\2\2\2NO\3\2\2\2OP\3\2\2\2PQ\7\23\2\2QR\5\16\b\2R\r\3\2\2"+
		"\2ST\7\'\2\2TU\7\24\2\2UY\7\'\2\2VX\5\4\3\2WV\3\2\2\2X[\3\2\2\2YW\3\2"+
		"\2\2YZ\3\2\2\2Z^\3\2\2\2[Y\3\2\2\2\\]\7\7\2\2]_\5\20\t\2^\\\3\2\2\2^_"+
		"\3\2\2\2_`\3\2\2\2`a\7\'\2\2ab\7\25\2\2bc\7\'\2\2c\17\3\2\2\2de\b\t\1"+
		"\2eo\7(\2\2fo\7\t\2\2go\5\22\n\2hi\7\22\2\2ij\5\20\t\2jk\7\23\2\2ko\3"+
		"\2\2\2lm\7#\2\2mo\5\20\t\5nd\3\2\2\2nf\3\2\2\2ng\3\2\2\2nh\3\2\2\2nl\3"+
		"\2\2\2o\u0087\3\2\2\2pq\f\n\2\2qr\7\30\2\2r\u0086\5\20\t\13st\f\t\2\2"+
		"tu\t\2\2\2u\u0086\5\20\t\nvw\f\b\2\2wx\t\3\2\2x\u0086\5\20\t\tyz\f\7\2"+
		"\2z{\t\4\2\2{\u0086\5\20\t\b|}\f\6\2\2}~\t\5\2\2~\u0086\5\20\t\7\177\u0080"+
		"\f\4\2\2\u0080\u0081\7$\2\2\u0081\u0086\5\20\t\5\u0082\u0083\f\3\2\2\u0083"+
		"\u0084\7%\2\2\u0084\u0086\5\20\t\4\u0085p\3\2\2\2\u0085s\3\2\2\2\u0085"+
		"v\3\2\2\2\u0085y\3\2\2\2\u0085|\3\2\2\2\u0085\177\3\2\2\2\u0085\u0082"+
		"\3\2\2\2\u0086\u0089\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088"+
		"\21\3\2\2\2\u0089\u0087\3\2\2\2\u008a\u008b\7(\2\2\u008b\u0094\7\22\2"+
		"\2\u008c\u0091\5\20\t\2\u008d\u008e\7\21\2\2\u008e\u0090\5\20\t\2\u008f"+
		"\u008d\3\2\2\2\u0090\u0093\3\2\2\2\u0091\u008f\3\2\2\2\u0091\u0092\3\2"+
		"\2\2\u0092\u0095\3\2\2\2\u0093\u0091\3\2\2\2\u0094\u008c\3\2\2\2\u0094"+
		"\u0095\3\2\2\2\u0095\u0096\3\2\2\2\u0096\u0097\7\23\2\2\u0097\23\3\2\2"+
		"\2\20\27!\':?KNY^n\u0085\u0087\u0091\u0094";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}