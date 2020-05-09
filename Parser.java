import java.io.InputStream;
import java.io.IOException;

public class Parser {
    private int lookaheadToken;
    //private double num;
    private InputStream in;

    public Parser(InputStream in) throws IOException{

        this.in = in;
        lookaheadToken = in.read();


    }

    public double eval() throws IOException, ParseError {
      	double rv = p_final(0);
      	if (lookaheadToken != '\n' && lookaheadToken != -1)
      	    throw new ParseError();
      	return rv;
    }


    private void consume(int symbol) throws IOException, ParseError {
          if (lookaheadToken != symbol)
      	    throw new ParseError();
        lookaheadToken = in.read();
    }

    private double p_final(double result) throws IOException, ParseError{
        if((lookaheadToken < '0' || lookaheadToken > '9') && lookaheadToken !='(')
      	    throw new ParseError();
        //System.out.print("consume() p_final\n");
        //consume(lookaheadToken);

        result= p_exp(result);
        return result;
    }

    private double p_exp(double result) throws IOException, ParseError{
          //System.out.print("FUNC p_exp  p_term\n");
          if(lookaheadToken < '0' || lookaheadToken > '9')
        	    throw new ParseError();
          result=p_term(result);
          //System.out.print("FUNC p_exp  p_exp2\n");
          result= p_exp2(result);
          return result;
    }

    private double p_exp2(double result) throws IOException, ParseError{
        if(lookaheadToken == '+'){
            //System.out.print("consume(+) p_exp\n");
            consume('+');
          //  System.out.print("FUNC p_exp2  p_term\n");
            result+= p_term(result);
            //System.out.print("FUNC p_exp2  p_exp2\n");
            result= p_exp2(result);


        }
        else if(lookaheadToken == '-'){
            //System.out.print("consume(-) p_exp\n");
            consume('-');
            //System.out.print("FUNC p_exp2  p_term\n");
            result-=p_term(result);
            //System.out.print("FUNC p_exp2  p_exp2\n");
            result=p_exp2(result);

        }
        //else
          //  throw new ParseError();
          return result;
    }

    private double p_term(double result) throws IOException, ParseError{
          //System.out.print("1 p_term\n");
          result=p_factor(result);
          //System.out.print("2 p_term\n");
          result= p_term2(result);
          return result;
    }

    private double p_term2(double result) throws IOException, ParseError{
        if(lookaheadToken == '*'){
          //System.out.print("1consume(*) p_term2\n");
          consume('*');
          result= result * p_factor(result);
          result= p_term2(result);
        }
        if(lookaheadToken == '/'){
          //System.out.print("3consume(/) p_term2\n");
          consume('/');
          result= result /p_factor(result);
          result= p_term2(result);
        }
        return result;
    }

    private double p_factor(double result) throws IOException, ParseError{
        if( (lookaheadToken < '0' || lookaheadToken > '9') && lookaheadToken !='('){
            throw new ParseError();
        }

        if(lookaheadToken == '(' ){
            consume('(');
            result=p_exp(result);
            consume(')');
        }
        else {
            int token = evalDigit(lookaheadToken);
            consume(lookaheadToken);
            result= p_num(token);
        }
        return result;
    }

    private double p_num(double result)throws IOException, ParseError{
        if(lookaheadToken >= '0' && lookaheadToken <= '9' ){
            double ans= (result*10) + evalDigit(lookaheadToken);
            consume(lookaheadToken);
            return p_num(ans);
        }
        return result;

    }

    private int evalDigit(int digit){
        return digit - '0';
    }
    public static void main(String[] args) {
      try {
          Parser evaluate = new Parser(System.in);
          System.out.println(evaluate.eval());

      }
      catch (IOException e) {
          System.err.println(e.getMessage());
      }
      catch(ParseError err){
          System.err.println(err.getMessage());
          //err.printStackTrace(System.err);
      }
        }
}
