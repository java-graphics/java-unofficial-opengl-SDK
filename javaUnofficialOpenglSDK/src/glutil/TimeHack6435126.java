/*
 * Copyright © 2014 <code@io7m.com> http://io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package glutil;

import java.util.concurrent.atomic.AtomicBoolean;

//@formatter:off

/**
 * Enable the workaround for JDK-6435126
 *
 * @see <a href="http://bugs.java.com/view_bug.do?bug_id=6435126">http://bugs.java.com/view_bug.do?bug_id=6435126</a>
 * 
 * https://io7m.github.io/timehack6435126/
 */

//@formatter:on

public final class TimeHack6435126
{
  private TimeHack6435126()
  {
    throw new AssertionError("Unreachable code!");
  }

  private static final AtomicBoolean ENABLED;

  static {
    ENABLED = new AtomicBoolean(false);
  }

  /**
   * Enable the operating system's high resolution timer.
   */

  public static void enableHighResolutionTimer()
  {
    if (TimeHack6435126.ENABLED.compareAndSet(false, true)) {
      final Thread t = new Thread() {
        @Override public void run()
        {
          try {
            Thread.sleep(Integer.MAX_VALUE);
          } catch (final InterruptedException e) {
            // Nothing
          }
        }
      };
      t.setName("timehack6435126");
      t.setDaemon(true);
      t.start();
    }
  }
}
