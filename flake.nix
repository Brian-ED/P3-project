{
  description = "A Nix-flake-based Java development environment";

  inputs.nixpkgs.url = "https://flakehub.com/f/NixOS/nixpkgs/0.1"; # unstable Nixpkgs

  outputs = { self, nixpkgs }:
    let
      javaVersion = 21; # Change this value to update the whole stack

      replaceJDK = final: prev: let
        jdk = prev."jdk${toString javaVersion}";
      in {
        jdk    = jdk;
        maven  = prev.maven .override { jdk_headless = jdk; };
        gradle = prev.gradle.override { java         = jdk; };
        lombok = prev.lombok.override { jdk          = jdk; };
      };

      supportedSystems = [
        "x86_64-linux"
        "aarch64-linux"
        "x86_64-darwin"
        "aarch64-darwin"
      ];
      forEachSupportedSystem = f:
        nixpkgs.lib.genAttrs supportedSystems ( system:
          f {
            pkgs = import nixpkgs {
              inherit system;
              overlays = [ replaceJDK ];
            };
          }
        );
    in
    {
      overlays.default = replaceJDK;

      devShells = forEachSupportedSystem (
        { pkgs }: {
          default = pkgs.mkShellNoCC {
            packages = with pkgs; [
              gcc
              gradle
              jdk
              maven
              ncurses
              patchelf
              zlib
            ];

            shellHook =
              let
                loadLombok = "-javaagent:${pkgs.lombok}/share/java/lombok.jar";
                prev = "\${JAVA_TOOL_OPTIONS:+ $JAVA_TOOL_OPTIONS}";
              in
              ''
                export JAVA_TOOL_OPTIONS="${loadLombok}${prev}"
              '';
          };
        }
      );
    };
}
