package net.yageek.lambertruby;


import java.lang.Long;
import java.io.IOException;
import org.jruby.Ruby;
import org.jruby.RubyArray;
import org.jruby.RubyClass;
import org.jruby.RubyFixnum;
import org.jruby.RubyFloat;
import org.jruby.RubyModule;
import org.jruby.RubyObject;
import org.jruby.anno.JRubyMethod;
import org.jruby.runtime.ObjectAllocator;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.load.BasicLibraryService;


import net.yageek.lambert.*;

public class LambertRuby implements BasicLibraryService {

  private Ruby runtime;

  public boolean basicLoad(Ruby runtime) throws IOException {
    this.runtime = runtime;

    RubyModule lambert = runtime.defineModule("Lambert");


    RubyClass lambertPoint = lambert.defineClassUnder("LambertPoint",runtime.getObject(), new ObjectAllocator() {

      public IRubyObject allocate(Ruby runtime, RubyClass rubyClass){

        return new LambertPoint(runtime, rubyClass);

  }

    });

      lambertPoint.defineAnnotatedMethods(LambertPoint.class);

       return true;
  }


  public class LambertPoint extends RubyObject{


    RubyFloat x, y, z;

    public LambertPoint(final Ruby runtime, RubyClass rubyClass){
      super(runtime, rubyClass);

    }

    @JRubyMethod
    public void wgs84(ThreadContext context, IRubyObject zone){

      long zoneInt = ((RubyFixnum) zone).getLongValue();

      double localX, localY, localZ;

      localX = x.getDoubleValue();
      localY = y.getDoubleValue();
      localZ = z.getDoubleValue();

      net.yageek.lambert.LambertPoint pt = net.yageek.lambert.Lambert.convertToWGS84Deg(localX,localY, net.yageek.lambert.LambertZone(zoneInt));



    }

  }
  }
