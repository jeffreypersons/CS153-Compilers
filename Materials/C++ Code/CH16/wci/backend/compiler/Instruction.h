/**
 * <h1>Instruction</h1>
 *
 * <p>Jasmin instructions.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef INSTRUCTION_H_
#define INSTRUCTION_H_

#include <map>

namespace wci { namespace backend { namespace compiler {

using namespace std;

enum class OpCode
{
    // Load constant
    ICONST_0, ICONST_1, ICONST_2, ICONST_3, ICONST_4, ICONST_5, ICONST_M1,
    FCONST_0, FCONST_1, FCONST_2, ACONST_NULL,
    BIPUSH, SIPUSH, LDC,

    // Load value or address
    ILOAD_0, ILOAD_1, ILOAD_2, ILOAD_3,
    FLOAD_0, FLOAD_1, FLOAD_2, FLOAD_3,
    ALOAD_0, ALOAD_1, ALOAD_2, ALOAD_3,
    ILOAD, FLOAD, ALOAD,
    GETSTATIC, GETFIELD,

    // Store value or address
    ISTORE_0, ISTORE_1, ISTORE_2, ISTORE_3,
    FSTORE_0, FSTORE_1, FSTORE_2, FSTORE_3,
    ASTORE_0, ASTORE_1, ASTORE_2, ASTORE_3,
    ISTORE, FSTORE, ASTORE,
    PUTSTATIC, PUTFIELD,

    // Operand stack
    POP, SWAP, DUP,

    // Arithmetic and logical
    IADD, FADD, ISUB, FSUB, IMUL, FMUL,
    IDIV, FDIV, IREM, FREM, INEG, FNEG,
    IINC, IAND, IOR, IXOR,

    // Type conversion and checking
    I2F, I2C, I2D, F2I, F2D, D2F,
    CHECKCAST,

    // Objects and arrays
    NEW, NEWARRAY, ANEWARRAY, MULTIANEWARRAY,
    IALOAD, FALOAD, BALOAD, CALOAD, AALOAD,
    IASTORE, FASTORE, BASTORE, CASTORE, AASTORE,

    // Compare and branch
    IFEQ, IFNE, IFLT, IFLE, IFGT, IFGE,
    IF_ICMPEQ, IF_ICMPNE, IF_ICMPLT, IF_ICMPLE, IF_ICMPGT, IF_ICMPGE,
    FCMPG, GOTO, LOOKUPSWITCH,

    // Call and return
    INVOKESTATIC, INVOKEVIRTUAL, INVOKENONVIRTUAL,
    RETURN, IRETURN, FRETURN, ARETURN,

    // No operation
    NOP,
};

// Load constant
constexpr OpCode ICONST_0 = OpCode::ICONST_0;
constexpr OpCode ICONST_1 = OpCode::ICONST_1;
constexpr OpCode ICONST_2 = OpCode::ICONST_2;
constexpr OpCode ICONST_3 = OpCode::ICONST_3;
constexpr OpCode ICONST_4 = OpCode::ICONST_4;
constexpr OpCode ICONST_5 = OpCode::ICONST_5;
constexpr OpCode ICONST_M1 = OpCode::ICONST_M1;
constexpr OpCode FCONST_0 = OpCode::FCONST_0;
constexpr OpCode FCONST_1 = OpCode::FCONST_1;
constexpr OpCode FCONST_2 = OpCode::FCONST_2;
constexpr OpCode ACONST_NULL = OpCode::ACONST_NULL;
constexpr OpCode BIPUSH = OpCode::BIPUSH;
constexpr OpCode SIPUSH = OpCode::SIPUSH;
constexpr OpCode LDC = OpCode::LDC;

// Load value or address
constexpr OpCode ILOAD_0 = OpCode::ILOAD_0;
constexpr OpCode ILOAD_1 = OpCode::ILOAD_1;
constexpr OpCode ILOAD_2 = OpCode::ILOAD_2;
constexpr OpCode ILOAD_3 = OpCode::ILOAD_3;
constexpr OpCode FLOAD_0 = OpCode::FLOAD_0;
constexpr OpCode FLOAD_1 = OpCode::FLOAD_1;
constexpr OpCode FLOAD_2 = OpCode::FLOAD_2;
constexpr OpCode FLOAD_3 = OpCode::FLOAD_3;
constexpr OpCode ALOAD_0 = OpCode::ALOAD_0;
constexpr OpCode ALOAD_1 = OpCode::ALOAD_1;
constexpr OpCode ALOAD_2 = OpCode::ALOAD_2;
constexpr OpCode ALOAD_3 = OpCode::ALOAD_3;
constexpr OpCode ILOAD = OpCode::ILOAD;
constexpr OpCode FLOAD = OpCode::FLOAD;
constexpr OpCode ALOAD = OpCode::ALOAD;
constexpr OpCode GETSTATIC = OpCode::GETSTATIC;
constexpr OpCode GETFIELD = OpCode::GETFIELD;

// Store value or address
constexpr OpCode ISTORE_0 = OpCode::ISTORE_0;
constexpr OpCode ISTORE_1 = OpCode::ISTORE_1;
constexpr OpCode ISTORE_2 = OpCode::ISTORE_2;
constexpr OpCode ISTORE_3 = OpCode::ISTORE_3;
constexpr OpCode FSTORE_0 = OpCode::FSTORE_0;
constexpr OpCode FSTORE_1 = OpCode::FSTORE_1;
constexpr OpCode FSTORE_2 = OpCode::FSTORE_2;
constexpr OpCode FSTORE_3 = OpCode::FSTORE_3;
constexpr OpCode ASTORE_0 = OpCode::ASTORE_0;
constexpr OpCode ASTORE_1 = OpCode::ASTORE_1;
constexpr OpCode ASTORE_2 = OpCode::ASTORE_2;
constexpr OpCode ASTORE_3 = OpCode::ASTORE_3;
constexpr OpCode ISTORE = OpCode::ISTORE;
constexpr OpCode FSTORE = OpCode::FSTORE;
constexpr OpCode ASTORE = OpCode::ASTORE;
constexpr OpCode PUTSTATIC = OpCode::PUTSTATIC;
constexpr OpCode PUTFIELD = OpCode::PUTFIELD;

// Operand stack
constexpr OpCode POP = OpCode::POP;
constexpr OpCode SWAP = OpCode::SWAP;
constexpr OpCode DUP = OpCode::DUP;

// Arithmetic and logical
constexpr OpCode IADD = OpCode::IADD;
constexpr OpCode FADD = OpCode::FADD;
constexpr OpCode ISUB = OpCode::ISUB;
constexpr OpCode FSUB = OpCode::FSUB;
constexpr OpCode IMUL = OpCode::IMUL;
constexpr OpCode FMUL = OpCode::FMUL;
constexpr OpCode IDIV = OpCode::IDIV;
constexpr OpCode FDIV = OpCode::FDIV;
constexpr OpCode IREM = OpCode::IREM;
constexpr OpCode FREM = OpCode::FREM;
constexpr OpCode INEG = OpCode::INEG;
constexpr OpCode FNEG = OpCode::FNEG;
constexpr OpCode IINC = OpCode::IINC;
constexpr OpCode IAND = OpCode::IAND;
constexpr OpCode IOR = OpCode::IOR;
constexpr OpCode IXOR = OpCode::IXOR;

// Type conversion and checking
constexpr OpCode I2F = OpCode::I2F;
constexpr OpCode I2C = OpCode::I2C;
constexpr OpCode I2D = OpCode::I2D;
constexpr OpCode F2I = OpCode::F2I;
constexpr OpCode F2D = OpCode::F2D;
constexpr OpCode D2F = OpCode::D2F;
constexpr OpCode CHECKCAST = OpCode::CHECKCAST;

// Objects and arrays
constexpr OpCode NEW = OpCode::NEW;
constexpr OpCode NEWARRAY = OpCode::NEWARRAY;
constexpr OpCode ANEWARRAY = OpCode::ANEWARRAY;
constexpr OpCode MULTIANEWARRAY = OpCode::MULTIANEWARRAY;
constexpr OpCode IALOAD = OpCode::IALOAD;
constexpr OpCode FALOAD = OpCode::FALOAD;
constexpr OpCode BALOAD = OpCode::BALOAD;
constexpr OpCode CALOAD = OpCode::CALOAD;
constexpr OpCode AALOAD = OpCode::AALOAD;
constexpr OpCode IASTORE = OpCode::IASTORE;
constexpr OpCode FASTORE = OpCode::FASTORE;
constexpr OpCode BASTORE = OpCode::BASTORE;
constexpr OpCode CASTORE = OpCode::CASTORE;
constexpr OpCode AASTORE = OpCode::AASTORE;

// Compare and branch
constexpr OpCode IFEQ = OpCode::IFEQ;
constexpr OpCode IFNE = OpCode::IFNE;
constexpr OpCode IFLT = OpCode::IFLT;
constexpr OpCode IFLE = OpCode::IFLE;
constexpr OpCode IFGT = OpCode::IFGT;
constexpr OpCode IFGE = OpCode::IFGE;
constexpr OpCode IF_ICMPEQ = OpCode::IF_ICMPEQ;
constexpr OpCode IF_ICMPNE = OpCode::IF_ICMPNE;
constexpr OpCode IF_ICMPLT = OpCode::IF_ICMPLT;
constexpr OpCode IF_ICMPLE = OpCode::IF_ICMPLE;
constexpr OpCode IF_ICMPGT = OpCode::IF_ICMPGT;
constexpr OpCode IF_ICMPGE = OpCode::IF_ICMPGE;
constexpr OpCode FCMPG = OpCode::FCMPG;
constexpr OpCode GOTO = OpCode::GOTO;
constexpr OpCode LOOKUPSWITCH = OpCode::LOOKUPSWITCH;

// Call and return
constexpr OpCode INVOKESTATIC = OpCode::INVOKESTATIC;
constexpr OpCode INVOKEVIRTUAL = OpCode::INVOKEVIRTUAL;
constexpr OpCode INVOKENONVIRTUAL = OpCode::INVOKENONVIRTUAL;
constexpr OpCode RETURN = OpCode::RETURN;
constexpr OpCode IRETURN = OpCode::IRETURN;
constexpr OpCode FRETURN = OpCode::FRETURN;
constexpr OpCode ARETURN = OpCode::ARETURN;

// No operation
constexpr OpCode NOP = OpCode::NOP;

class Instruction
{
public:
    /**
     * Constructor
     */
    Instruction();

    /**
     * Print an instruction opcode.
     */
    friend ostream& operator << (ostream& os, const OpCode& opcode);

    /**
     * Initialize the static maps.
     */
    static void initialize();

private:
    static map<OpCode, string> TEXT;

    static bool INITIALIZED;
};

}}}  // namespace wci::backend::compiler

#endif /* INSTRUCTION_H_ */
