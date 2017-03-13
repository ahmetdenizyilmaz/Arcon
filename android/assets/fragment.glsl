#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

void main() {
        vec3 color = texture2D(u_texture, v_texCoords).rgb;
        float gray = (color.r + color.g + color.b) / 3.0;
        vec3 grayscale = vec3(gray);

        gl_FragColor = vec4(grayscale, 1.0);
}
    /*
        if (gl_FragCoord.x%2==0)
        {
        if(gl_FragCoord.y%2==0)
        {
         gl_FragColor=vColor;
        }
        else
        {
        gl_FragColor=texture2D(u_texture, vec2( vTexCoord.x, vTexCoord.y-1))
        }
        
        }
        else
        {
          if(gl_FragCoord.y%2==0)
        {
          gl_FragColor=texture2D(u_texture, vec2( vTexCoord.x-1, vTexCoord.y))
        }
        else
        {
        gl_FragColor=texture2D(u_texture, vec2( vTexCoord.x-1, vTexCoord.y-1))
        }
        }
        */
        
        
        
        
        
        
        
        
        
        
        
        
}