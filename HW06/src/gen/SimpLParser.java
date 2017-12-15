// Generated from /Users/KishikawaItaru/GitHub/CS153-Assignments/HW06/src/SimpL.g4 by ANTLR 4.7
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
public class SimpLParser extends Parser {
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
	public static final int
		RULE_program = 0, RULE_stat = 1, RULE_declaration = 2, RULE_assignment = 3, 
		RULE_while_loop = 4, RULE_conditional = 5, RULE_func_def = 6, RULE_block = 7, 
		RULE_expr = 8, RULE_func_call = 9;
	public static final String[] ruleNames = {
		"program", "stat", "declaration", "assignment", "while_loop", "conditional", 
		"func_def", "block", "expr", "func_call"
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

	@Override
	public String getGrammarFileName() { return "SimpL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SimpLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SimpLVisitor ) return ((SimpLVisitor<? extends T>)visitor).visitProgram(this);
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
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__4) | (1L << TYPE) | (1L << LITERAL) | (1L << LPAREN) | (1L << NOT) | (1L << NAME))) != 0)) {
				{
				{
				setState(20);
				stat();
				}
				}
				setState(25);
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

	public static class StatContext extends ParserRuleContext {
		public Func_defContext func_def() {
			return getRuleContext(Func_defContext.class,0);
		}
		public ConditionalContext conditional() {
			return getRuleContext(ConditionalContext.class,0);
		}
		public While_loopContext while_loop() {
			return getRuleContext(While_loopContext.class,0);
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
		public TerminalNode EOL() { return getToken(SimpLParser.EOL, 0); }
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).enterStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).exitStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SimpLVisitor ) return ((SimpLVisitor<? extends T>)visitor).visitStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_stat);
		try {
			setState(34);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(26);
				func_def();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(27);
				conditional();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(28);
				while_loop();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(29);
				declaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(30);
				assignment();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(31);
				expr(0);
				setState(32);
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
		public TerminalNode TYPE() { return getToken(SimpLParser.TYPE, 0); }
		public TerminalNode NAME() { return getToken(SimpLParser.NAME, 0); }
		public TerminalNode EOL() { return getToken(SimpLParser.EOL, 0); }
		public TerminalNode ASSIGN() { return getToken(SimpLParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SimpLVisitor ) return ((SimpLVisitor<? extends T>)visitor).visitDeclaration(this);
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
			setState(36);
			match(TYPE);
			setState(37);
			match(NAME);
			setState(40);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(38);
				match(ASSIGN);
				setState(39);
				expr(0);
				}
			}

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

	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(SimpLParser.NAME, 0); }
		public TerminalNode ASSIGN() { return getToken(SimpLParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode EOL() { return getToken(SimpLParser.EOL, 0); }
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SimpLVisitor ) return ((SimpLVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			match(NAME);
			setState(45);
			match(ASSIGN);
			setState(46);
			expr(0);
			setState(47);
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

	public static class While_loopContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public While_loopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_while_loop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).enterWhile_loop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).exitWhile_loop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SimpLVisitor ) return ((SimpLVisitor<? extends T>)visitor).visitWhile_loop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final While_loopContext while_loop() throws RecognitionException {
		While_loopContext _localctx = new While_loopContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_while_loop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			match(T__0);
			setState(50);
			expr(0);
			setState(51);
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

	public static class ConditionalContext extends ParserRuleContext {
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
		public ConditionalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditional; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).enterConditional(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).exitConditional(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SimpLVisitor ) return ((SimpLVisitor<? extends T>)visitor).visitConditional(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalContext conditional() throws RecognitionException {
		ConditionalContext _localctx = new ConditionalContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_conditional);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(53);
			match(T__1);
			setState(54);
			expr(0);
			setState(55);
			block();
			}
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(57);
				match(T__2);
				setState(58);
				expr(0);
				setState(59);
				block();
				}
				}
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(68);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(66);
				match(T__3);
				setState(67);
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
		public List<TerminalNode> NAME() { return getTokens(SimpLParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(SimpLParser.NAME, i);
		}
		public TerminalNode LPAREN() { return getToken(SimpLParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(SimpLParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TerminalNode> TYPE() { return getTokens(SimpLParser.TYPE); }
		public TerminalNode TYPE(int i) {
			return getToken(SimpLParser.TYPE, i);
		}
		public List<TerminalNode> SEPARATOR() { return getTokens(SimpLParser.SEPARATOR); }
		public TerminalNode SEPARATOR(int i) {
			return getToken(SimpLParser.SEPARATOR, i);
		}
		public Func_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).enterFunc_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).exitFunc_def(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SimpLVisitor ) return ((SimpLVisitor<? extends T>)visitor).visitFunc_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Func_defContext func_def() throws RecognitionException {
		Func_defContext _localctx = new Func_defContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_func_def);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			_la = _input.LA(1);
			if ( !(_la==T__4 || _la==TYPE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(71);
			match(NAME);
			setState(72);
			match(LPAREN);
			setState(83);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE) {
				{
				setState(73);
				match(TYPE);
				setState(74);
				match(NAME);
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SEPARATOR) {
					{
					{
					setState(75);
					match(SEPARATOR);
					setState(76);
					match(TYPE);
					setState(77);
					match(NAME);
					}
					}
					setState(82);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(85);
			match(RPAREN);
			setState(86);
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
		public List<TerminalNode> EOL() { return getTokens(SimpLParser.EOL); }
		public TerminalNode EOL(int i) {
			return getToken(SimpLParser.EOL, i);
		}
		public TerminalNode LCURL() { return getToken(SimpLParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(SimpLParser.RCURL, 0); }
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
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
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SimpLVisitor ) return ((SimpLVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_block);
		int _la;
		try {
			setState(122);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(88);
				match(EOL);
				setState(89);
				match(LCURL);
				setState(90);
				match(EOL);
				setState(94);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__4) | (1L << TYPE) | (1L << LITERAL) | (1L << LPAREN) | (1L << NOT) | (1L << NAME))) != 0)) {
					{
					{
					setState(91);
					stat();
					}
					}
					setState(96);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(101);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__5) {
					{
					setState(97);
					match(T__5);
					setState(98);
					expr(0);
					setState(99);
					match(EOL);
					}
				}

				setState(103);
				match(RCURL);
				setState(104);
				match(EOL);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(105);
				match(EOL);
				setState(106);
				match(LCURL);
				setState(107);
				match(EOL);
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__4) | (1L << TYPE) | (1L << LITERAL) | (1L << LPAREN) | (1L << NOT) | (1L << NAME))) != 0)) {
					{
					{
					setState(108);
					stat();
					}
					}
					setState(113);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(118);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__5) {
					{
					setState(114);
					match(T__5);
					setState(115);
					expr(0);
					setState(116);
					match(EOL);
					}
				}

				setState(120);
				match(EOL);
				notifyErrorListeners("Missing closing '}'");
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

	public static class ExprContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(SimpLParser.NAME, 0); }
		public TerminalNode LITERAL() { return getToken(SimpLParser.LITERAL, 0); }
		public Func_callContext func_call() {
			return getRuleContext(Func_callContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(SimpLParser.LPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(SimpLParser.RPAREN, 0); }
		public TerminalNode NOT() { return getToken(SimpLParser.NOT, 0); }
		public TerminalNode POW() { return getToken(SimpLParser.POW, 0); }
		public TerminalNode MUL() { return getToken(SimpLParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(SimpLParser.DIV, 0); }
		public TerminalNode ADD() { return getToken(SimpLParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(SimpLParser.SUB, 0); }
		public TerminalNode LT() { return getToken(SimpLParser.LT, 0); }
		public TerminalNode GT() { return getToken(SimpLParser.GT, 0); }
		public TerminalNode LTE() { return getToken(SimpLParser.LTE, 0); }
		public TerminalNode GTE() { return getToken(SimpLParser.GTE, 0); }
		public TerminalNode EQ() { return getToken(SimpLParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(SimpLParser.NEQ, 0); }
		public TerminalNode AND() { return getToken(SimpLParser.AND, 0); }
		public TerminalNode OR() { return getToken(SimpLParser.OR, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SimpLVisitor ) return ((SimpLVisitor<? extends T>)visitor).visitExpr(this);
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
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(125);
				match(NAME);
				}
				break;
			case 2:
				{
				setState(126);
				match(LITERAL);
				}
				break;
			case 3:
				{
				setState(127);
				func_call();
				}
				break;
			case 4:
				{
				setState(128);
				match(LPAREN);
				setState(129);
				expr(0);
				setState(130);
				match(RPAREN);
				}
				break;
			case 5:
				{
				setState(132);
				match(NOT);
				setState(133);
				expr(3);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(159);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(157);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(136);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(137);
						match(POW);
						setState(138);
						expr(9);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(139);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(140);
						_la = _input.LA(1);
						if ( !(_la==MUL || _la==DIV) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(141);
						expr(8);
						}
						break;
					case 3:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(142);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(143);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(144);
						expr(7);
						}
						break;
					case 4:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(145);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(146);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LT) | (1L << GT) | (1L << LTE) | (1L << GTE))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(147);
						expr(6);
						}
						break;
					case 5:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(148);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(149);
						_la = _input.LA(1);
						if ( !(_la==EQ || _la==NEQ) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(150);
						expr(5);
						}
						break;
					case 6:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(151);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(152);
						match(AND);
						setState(153);
						expr(3);
						}
						break;
					case 7:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(154);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(155);
						match(OR);
						setState(156);
						expr(2);
						}
						break;
					}
					} 
				}
				setState(161);
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
		public TerminalNode NAME() { return getToken(SimpLParser.NAME, 0); }
		public TerminalNode LPAREN() { return getToken(SimpLParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(SimpLParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> SEPARATOR() { return getTokens(SimpLParser.SEPARATOR); }
		public TerminalNode SEPARATOR(int i) {
			return getToken(SimpLParser.SEPARATOR, i);
		}
		public Func_callContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_call; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).enterFunc_call(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SimpLListener ) ((SimpLListener)listener).exitFunc_call(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SimpLVisitor ) return ((SimpLVisitor<? extends T>)visitor).visitFunc_call(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Func_callContext func_call() throws RecognitionException {
		Func_callContext _localctx = new Func_callContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_func_call);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			match(NAME);
			setState(163);
			match(LPAREN);
			setState(172);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LITERAL) | (1L << LPAREN) | (1L << NOT) | (1L << NAME))) != 0)) {
				{
				setState(164);
				expr(0);
				setState(169);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SEPARATOR) {
					{
					{
					setState(165);
					match(SEPARATOR);
					setState(166);
					expr(0);
					}
					}
					setState(171);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(174);
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
		case 8:
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3,\u00b3\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\3\2\7\2\30\n\2\f\2\16\2\33\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5"+
		"\3%\n\3\3\4\3\4\3\4\3\4\5\4+\n\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3"+
		"\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7@\n\7\f\7\16\7C\13\7\3\7\3\7"+
		"\5\7G\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\bQ\n\b\f\b\16\bT\13\b\5\b"+
		"V\n\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\7\t_\n\t\f\t\16\tb\13\t\3\t\3\t\3\t"+
		"\3\t\5\th\n\t\3\t\3\t\3\t\3\t\3\t\3\t\7\tp\n\t\f\t\16\ts\13\t\3\t\3\t"+
		"\3\t\3\t\5\ty\n\t\3\t\3\t\5\t}\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\5\n\u0089\n\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u00a0\n\n\f\n\16\n\u00a3\13\n\3\13"+
		"\3\13\3\13\3\13\3\13\7\13\u00aa\n\13\f\13\16\13\u00ad\13\13\5\13\u00af"+
		"\n\13\3\13\3\13\3\13\2\3\22\f\2\4\6\b\n\f\16\20\22\24\2\7\4\2\7\7\t\t"+
		"\3\2\32\33\3\2\34\35\3\2\36!\3\2\"#\2\u00c5\2\31\3\2\2\2\4$\3\2\2\2\6"+
		"&\3\2\2\2\b.\3\2\2\2\n\63\3\2\2\2\f\67\3\2\2\2\16H\3\2\2\2\20|\3\2\2\2"+
		"\22\u0088\3\2\2\2\24\u00a4\3\2\2\2\26\30\5\4\3\2\27\26\3\2\2\2\30\33\3"+
		"\2\2\2\31\27\3\2\2\2\31\32\3\2\2\2\32\3\3\2\2\2\33\31\3\2\2\2\34%\5\16"+
		"\b\2\35%\5\f\7\2\36%\5\n\6\2\37%\5\6\4\2 %\5\b\5\2!\"\5\22\n\2\"#\7(\2"+
		"\2#%\3\2\2\2$\34\3\2\2\2$\35\3\2\2\2$\36\3\2\2\2$\37\3\2\2\2$ \3\2\2\2"+
		"$!\3\2\2\2%\5\3\2\2\2&\'\7\t\2\2\'*\7)\2\2()\7\'\2\2)+\5\22\n\2*(\3\2"+
		"\2\2*+\3\2\2\2+,\3\2\2\2,-\7(\2\2-\7\3\2\2\2./\7)\2\2/\60\7\'\2\2\60\61"+
		"\5\22\n\2\61\62\7(\2\2\62\t\3\2\2\2\63\64\7\3\2\2\64\65\5\22\n\2\65\66"+
		"\5\20\t\2\66\13\3\2\2\2\678\7\4\2\289\5\22\n\29:\5\20\t\2:A\3\2\2\2;<"+
		"\7\5\2\2<=\5\22\n\2=>\5\20\t\2>@\3\2\2\2?;\3\2\2\2@C\3\2\2\2A?\3\2\2\2"+
		"AB\3\2\2\2BF\3\2\2\2CA\3\2\2\2DE\7\6\2\2EG\5\20\t\2FD\3\2\2\2FG\3\2\2"+
		"\2G\r\3\2\2\2HI\t\2\2\2IJ\7)\2\2JU\7\23\2\2KL\7\t\2\2LR\7)\2\2MN\7\22"+
		"\2\2NO\7\t\2\2OQ\7)\2\2PM\3\2\2\2QT\3\2\2\2RP\3\2\2\2RS\3\2\2\2SV\3\2"+
		"\2\2TR\3\2\2\2UK\3\2\2\2UV\3\2\2\2VW\3\2\2\2WX\7\24\2\2XY\5\20\t\2Y\17"+
		"\3\2\2\2Z[\7(\2\2[\\\7\25\2\2\\`\7(\2\2]_\5\4\3\2^]\3\2\2\2_b\3\2\2\2"+
		"`^\3\2\2\2`a\3\2\2\2ag\3\2\2\2b`\3\2\2\2cd\7\b\2\2de\5\22\n\2ef\7(\2\2"+
		"fh\3\2\2\2gc\3\2\2\2gh\3\2\2\2hi\3\2\2\2ij\7\26\2\2j}\7(\2\2kl\7(\2\2"+
		"lm\7\25\2\2mq\7(\2\2np\5\4\3\2on\3\2\2\2ps\3\2\2\2qo\3\2\2\2qr\3\2\2\2"+
		"rx\3\2\2\2sq\3\2\2\2tu\7\b\2\2uv\5\22\n\2vw\7(\2\2wy\3\2\2\2xt\3\2\2\2"+
		"xy\3\2\2\2yz\3\2\2\2z{\7(\2\2{}\b\t\1\2|Z\3\2\2\2|k\3\2\2\2}\21\3\2\2"+
		"\2~\177\b\n\1\2\177\u0089\7)\2\2\u0080\u0089\7\n\2\2\u0081\u0089\5\24"+
		"\13\2\u0082\u0083\7\23\2\2\u0083\u0084\5\22\n\2\u0084\u0085\7\24\2\2\u0085"+
		"\u0089\3\2\2\2\u0086\u0087\7$\2\2\u0087\u0089\5\22\n\5\u0088~\3\2\2\2"+
		"\u0088\u0080\3\2\2\2\u0088\u0081\3\2\2\2\u0088\u0082\3\2\2\2\u0088\u0086"+
		"\3\2\2\2\u0089\u00a1\3\2\2\2\u008a\u008b\f\n\2\2\u008b\u008c\7\31\2\2"+
		"\u008c\u00a0\5\22\n\13\u008d\u008e\f\t\2\2\u008e\u008f\t\3\2\2\u008f\u00a0"+
		"\5\22\n\n\u0090\u0091\f\b\2\2\u0091\u0092\t\4\2\2\u0092\u00a0\5\22\n\t"+
		"\u0093\u0094\f\7\2\2\u0094\u0095\t\5\2\2\u0095\u00a0\5\22\n\b\u0096\u0097"+
		"\f\6\2\2\u0097\u0098\t\6\2\2\u0098\u00a0\5\22\n\7\u0099\u009a\f\4\2\2"+
		"\u009a\u009b\7%\2\2\u009b\u00a0\5\22\n\5\u009c\u009d\f\3\2\2\u009d\u009e"+
		"\7&\2\2\u009e\u00a0\5\22\n\4\u009f\u008a\3\2\2\2\u009f\u008d\3\2\2\2\u009f"+
		"\u0090\3\2\2\2\u009f\u0093\3\2\2\2\u009f\u0096\3\2\2\2\u009f\u0099\3\2"+
		"\2\2\u009f\u009c\3\2\2\2\u00a0\u00a3\3\2\2\2\u00a1\u009f\3\2\2\2\u00a1"+
		"\u00a2\3\2\2\2\u00a2\23\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a4\u00a5\7)\2\2"+
		"\u00a5\u00ae\7\23\2\2\u00a6\u00ab\5\22\n\2\u00a7\u00a8\7\22\2\2\u00a8"+
		"\u00aa\5\22\n\2\u00a9\u00a7\3\2\2\2\u00aa\u00ad\3\2\2\2\u00ab\u00a9\3"+
		"\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae"+
		"\u00a6\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b1\7\24"+
		"\2\2\u00b1\25\3\2\2\2\23\31$*AFRU`gqx|\u0088\u009f\u00a1\u00ab\u00ae";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}